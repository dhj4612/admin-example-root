package org.example.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.example.admin.mapper.SysUserMapper;
import org.example.admin.model.entity.SysMenu;
import org.example.admin.model.entity.SysRole;
import org.example.admin.model.entity.SysUser;
import org.example.admin.model.entity.SysUserRole;
import org.example.admin.model.event.SyncUserRoleAuthorityEvent;
import org.example.admin.model.param.*;
import org.example.admin.model.result.*;
import org.example.admin.service.SysMenuService;
import org.example.admin.service.SysRoleService;
import org.example.admin.service.SysUserRoleService;
import org.example.admin.service.SysUserService;
import org.example.framework.common.base.BaseEntity;
import org.example.framework.common.base.BasePageResult;
import org.example.framework.common.exception.BizException;
import org.example.framework.database.core.DbEncryptHelper;
import org.example.framework.security.core.user.SecurityUserContext;
import org.example.framework.security.core.user.UserAuthorized;
import org.example.framework.security.core.utils.JwtUtil;
import org.example.framework.utils.AesUtil;
import org.example.framework.utils.RedisUtil;
import org.example.framework.utils.TreeUtil;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;


/**
 * 用户管理
 *
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final PasswordEncoder passwordEncoder;
    private final SysMenuService sysMenuService;
    private final SysUserRoleService sysUserRoleService;
    private final SysRoleService sysRoleService;
    private final ApplicationEventPublisher publisher;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void userSaveOrUpdate(UserSaveOrUpdateParam param) {
        final boolean update = param.id() != null;
        SysUser sysUser;
        if (update) {
            sysUser = lambdaQuery()
                    .eq(SysUser::getStatus, 1)
                    .eq(BaseEntity::getId, param.id()).one();
            Assert.notNull(sysUser, "用户不存在或已禁用");
            Assert.state(!Objects.equals(sysUser.getSuperAdmin(), 1), "管理员账户不能修改");

            boolean change = false;
            LambdaQueryChainWrapper<SysUser> condition = lambdaQuery();
            if (!Objects.equals(sysUser.getMobile(), param.phone())) {
                condition.eq(SysUser::getMobile, param.phone());
                change = true;
            }
            if (!Objects.equals(sysUser.getUsername(), param.username())) {
                condition.or().eq(SysUser::getUsername, param.username());
                change = true;
            }
            if (change) {
                Assert.state(!condition.exists(), "手机号或用户名已存在");
            }
        } else {
            Assert.isNull(lambdaQuery()
                    .eq(SysUser::getMobile, DbEncryptHelper.encrypt(param.phone()))
                    .or()
                    .eq(SysUser::getUsername, param.username())
                    .one(), "手机号或用户名已存在");
            sysUser = new SysUser();
        }

        sysUser.setUsername(param.username());
        sysUser.setPassword(passwordEncoder.encode(param.password()));
        sysUser.setMobile(param.phone());
        sysUser.setStatus(1);
        sysUser.setSuperAdmin(0);

        saveOrUpdate(sysUser);

        sysUserRoleService.syncUserRole(sysUser, param.roleIds());

        if (update) {
            publisher.publishEvent(new SyncUserRoleAuthorityEvent());
        }
    }

    @Override
    public UserLoginResult phoneLogin(UserPhoneLoginParam param) {
        SysUser sysUser = lambdaQuery()
                .eq(SysUser::getMobile, DbEncryptHelper.encrypt(param.phone()))
                .one();
        Assert.notNull(sysUser, "用户不存在");
        Assert.state(Objects.equals(sysUser.getStatus(), 1), "用户已被禁用");
        Assert.state(passwordEncoder.matches(param.password(), sysUser.getPassword()), "密码错误");
        UserAuthorized userAuthorized = new UserAuthorized()
                .setId(sysUser.getId())
                .setAuthorities(getUserAuthoritiesByUserId(sysUser.getId()))
                .setRoles(getUserRolesByUserId(sysUser.getId()))
                .setSuperAdmin(Objects.equals(sysUser.getSuperAdmin(), 1));

        String jwt = JwtUtil.createJwtStrAndCache(userAuthorized);
        String refreshJwtStr = JwtUtil.createRefreshJwtStr(userAuthorized);
        return new UserLoginResult(jwt, refreshJwtStr, sysUser.getUsername(), sysUser.getSuperAdmin() == 1, sysUser.getAvatar());
    }

    @Override
    public UserInfoResult getUserInfo() {
        SysUser sysUser = lambdaQuery()
                .eq(BaseEntity::getId, SecurityUserContext.ensureGetUserId())
                .eq(SysUser::getStatus, 1)
                .one();
        Optional.ofNullable(sysUser)
                .orElseThrow(() -> BizException.valueOfMsg("用户不存在或已禁用"));
        return new UserInfoResult(sysUser.getUsername(), sysUser.getAvatar(), Objects.equals(sysUser.getSuperAdmin(), 1));
    }

    @Override
    public Set<String> getUserAuthoritiesAndRoles() {
        UserAuthorized userAuthorized = SecurityUserContext.ensureGetUser();
        Set<String> authoritiesAndRoles = new HashSet<>(getUserAuthoritiesByUserId(userAuthorized.getId()));
        authoritiesAndRoles.addAll(getUserRolesByUserId(userAuthorized.getId()));
        return authoritiesAndRoles;
    }

    @Override
    public Set<String> getUserAuthoritiesByUserId(Integer id) {
        SysUser user = getById(id);
        if (user == null || Objects.equals(user.getStatus(), 0)) {
            throw BizException.valueOfMsg("用户不存在或已禁用");
        }
        if (Objects.equals(user.getSuperAdmin(), 1)) {
            Set<String> authorities = sysMenuService.lambdaQuery()
                    .isNotNull(SysMenu::getAuthority)
                    .list()
                    .stream()
                    .flatMap(item -> Arrays.stream(item.getAuthority().split(",")))
                    .collect(Collectors.toSet());
            authorities.add("*");
            return authorities;
        }
        return baseMapper.selectAuthoritiesByUserId(id)
                .stream()
                .flatMap(item -> Arrays.stream(item.split(",")))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getUserRolesByUserId(Integer id) {
        SysUser user = getById(id);
        if (user == null || Objects.equals(user.getStatus(), 0)) {
            throw BizException.valueOfMsg("用户不存在或已禁用");
        }
        if (Objects.equals(user.getStatus(), 1)) {
            Set<String> roles = sysRoleService.lambdaQuery()
                    .list()
                    .stream()
                    .map(SysRole::getRoleCode).collect(Collectors.toSet());
            roles.add("admin");
            return roles;
        }
        return sysUserRoleService.getUserRolesByUserId(id);
    }

    @Override
    public List<SysMenuResult> getUserMenuNav() {
        UserAuthorized userAuthorized = SecurityUserContext.ensureGetUser();
        List<SysMenu> menus;
        if (userAuthorized.isSuperAdmin()) {
            menus = sysMenuService.lambdaQuery()
                    .eq(SysMenu::getType, 0)
                    .list();
        } else {
            menus = sysMenuService.getUserMenuList(userAuthorized, 0);
        }
        menus.sort(Comparator.comparing(SysMenu::getSort));
        return TreeUtil.build(menus.stream().map(item -> BeanUtil.toBean(item, SysMenuResult.class)).toList());
    }

    @Override
    public void logout() {
        RedisUtil.StringRedisTemplate.delete("login:session:" +
                AesUtil.encrypt(String.valueOf(SecurityUserContext.ensureGetUser().getId())));
    }

    @Override
    public BasePageResult<SysUserResult> userList(UserListQueryParam param) {
        return new BasePageResult<>(lambdaQuery()
                .page(new Page<>(param.getCurrent(), param.getPageSize()))
                .convert(item -> {
                    item.setMobile(DbEncryptHelper.decrypt(item.getMobile()));
                    return BeanUtil.copyProperties(item, SysUserResult.class);
                })
        );
    }

    @Override
    public SysUserInfoResult getUpdateUserInfo(SysUserInfoQueryParam param) {
        SysUser user = getById(param.id());
        if (user == null) {
            throw BizException.valueOfMsg("用户不存在");
        }
        SysUserInfoResult sysUserInfoResult = BeanUtil.copyProperties(user, SysUserInfoResult.class);

        sysUserInfoResult.setRoleIds(
                sysUserRoleService.lambdaQuery()
                        .eq(SysUserRole::getUserId, user.getId())
                        .list().stream().map(SysUserRole::getRoleId)
                        .collect(Collectors.toSet()));

        return sysUserInfoResult;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delUser(UserDelParam param) {
        boolean removed = lambdaUpdate()
                .eq(SysUser::getId, param.id())
                .remove();

        if (removed) {
            sysUserRoleService.lambdaUpdate()
                    .eq(SysUserRole::getUserId, param.id())
                    .remove();
            // 删除登录会话
            RedisUtil.StringRedisTemplate.delete("login:session:" +
                    AesUtil.encrypt(String.valueOf(param.id())));
        }
    }
}
