package org.example.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.example.admin.mapper.SysUserMapper;
import org.example.admin.model.entity.SysMenu;
import org.example.admin.model.entity.SysRole;
import org.example.admin.model.param.UserPhoneLoginParam;
import org.example.admin.model.param.UserSaveOrUpdateParam;
import org.example.admin.model.result.SysMenuResult;
import org.example.admin.model.result.UserInfoResult;
import org.example.admin.model.result.UserLoginResult;
import org.example.admin.model.entity.SysUser;
import org.example.admin.service.SysMenuService;
import org.example.admin.service.SysRoleService;
import org.example.admin.service.SysUserRoleService;
import org.example.admin.service.SysUserService;
import org.example.framework.common.base.BaseEntity;
import org.example.framework.common.exception.BizException;
import org.example.framework.database.core.DbEncryptHelper;
import org.example.framework.security.core.user.SecurityUserContext;
import org.example.framework.security.core.user.UserAuthorized;
import org.example.framework.security.core.utils.JwtUtil;
import org.example.framework.utils.TreeUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
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
    private final StringRedisTemplate stringRedisTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void userSaveOrUpdate(UserSaveOrUpdateParam param) {
        final boolean update = param.id() != null;
        SysUser sysUser = lambdaQuery()
                .eq(SysUser::getMobile, DbEncryptHelper.encrypt(param.phone()))
                .or()
                .eq(SysUser::getUsername, param.username())
                .one();
        Assert.isNull(sysUser, "手机号或用户名已经存在");

        if (update) {
            sysUser = lambdaQuery()
                    .eq(SysUser::getStatus, 1)
                    .eq(BaseEntity::getId, param.id()).one();
            Assert.notNull(sysUser, "用户不存在或已禁用");
            Assert.state(!Objects.equals(sysUser.getSuperAdmin(), 1), "管理员账户不能修改");
        } else {
            sysUser = new SysUser();
        }

        sysUser.setUsername(param.username());
        sysUser.setPassword(passwordEncoder.encode(param.password()));
        sysUser.setMobile(param.phone());
        sysUser.setStatus(1);
        sysUser.setSuperAdmin(0);

        saveOrUpdate(sysUser);

        sysUserRoleService.saveOrUpdateUserRoleRelation(sysUser, param.roleIds());
    }

    @Override
    public UserLoginResult phoneLogin(UserPhoneLoginParam param) {
        SysUser sysUser = lambdaQuery()
                .eq(SysUser::getMobile, DbEncryptHelper.encrypt(param.phone()))
                .one();
        Assert.notNull(sysUser, "用户不存在");
        Assert.state(Objects.equals(sysUser.getStatus(), 1), "用户已被禁用");
        Assert.state(passwordEncoder.matches(param.password(), sysUser.getPassword()), "密码错误");

        boolean isSuperAdmin = Objects.equals(sysUser.getSuperAdmin(), 1);
        Set<String> roles;
        if (isSuperAdmin) {
            roles = sysRoleService.list().stream().map(SysRole::getRoleCode).collect(Collectors.toSet());
        } else {
            roles = sysUserRoleService.getUserRolesByUserId(sysUser.getId());
        }
        roles.add("admin");
        UserAuthorized userAuthorized = new UserAuthorized()
                .setId(sysUser.getId())
                .setAuthorities(
                        isSuperAdmin ?
                                sysMenuService.lambdaQuery()
                                        .isNotNull(SysMenu::getAuthority)
                                        .list()
                                        .stream()
                                        .flatMap(item -> Arrays.stream(item.getAuthority().split(",")))
                                        .collect(Collectors.toSet())
                                : sysMenuService.getUserAuthsByUsrId(sysUser.getId())
                                .stream()
                                .flatMap(item -> Arrays.stream(item.split(",")))
                                .collect(Collectors.toSet())
                )
                .setRoles(roles)
                .setSuperAdmin(isSuperAdmin);

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
    public Set<String> getUserAuthorities() {
        UserAuthorized userAuthorized = SecurityUserContext.ensureGetUser();
        if (userAuthorized.isSuperAdmin()) {
            return sysMenuService.lambdaQuery()
                    .isNotNull(SysMenu::getAuthority)
                    .list()
                    .stream()
                    .flatMap(item -> Arrays.stream(item.getAuthority().split(",")))
                    .collect(Collectors.toSet());
        }
        return baseMapper.selectAuthoritiesByUserId(SecurityUserContext.ensureGetUserId())
                .stream()
                .flatMap(item -> Arrays.stream(item.split(",")))
                .collect(Collectors.toSet());
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
        menus.sort(Comparator.comparing(SysMenu::getSort).reversed());
        return TreeUtil.build(menus.stream().map(item -> BeanUtil.toBean(item, SysMenuResult.class)).toList());
    }

    @Override
    public void logout() {
        stringRedisTemplate.delete("login:session:" + SecurityUserContext.ensureGetUser().getId());
    }
}
