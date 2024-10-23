package org.example.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.example.admin.mapper.SysRoleMapper;
import org.example.admin.model.entity.SysRole;
import org.example.admin.model.entity.SysRoleMenu;
import org.example.admin.model.entity.SysUserRole;
import org.example.admin.model.event.SyncUserRoleAuthorityEvent;
import org.example.admin.model.param.RoleAddOrUpdateParam;
import org.example.admin.model.param.RoleDelParam;
import org.example.admin.model.param.RoleInfoQueryParam;
import org.example.admin.model.param.RoleListQueryParam;
import org.example.admin.model.result.SysRoleInfoResult;
import org.example.admin.model.result.SysRoleResult;
import org.example.admin.service.SysRoleMenuService;
import org.example.admin.service.SysRoleService;
import org.example.admin.service.SysUserRoleService;
import org.example.framework.common.base.BasePageResult;
import org.example.framework.common.exception.BizException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;

/**
 * 角色
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    private final SysRoleMenuService sysRoleMenuService;
    private final SysUserRoleService sysUserRoleService;
    private final ApplicationEventPublisher publisher;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdateRole(RoleAddOrUpdateParam param) {
        final boolean update = param.id() != null;
        SysRole sysRole;

        if (update) {
            sysRole = getById(param.id());
            Assert.notNull(sysRole, "角色不存在或已禁用");

            boolean change = false;
            LambdaQueryChainWrapper<SysRole> condition = lambdaQuery();
            if (!Objects.equals(sysRole.getName(), param.name())) {
                condition.eq(SysRole::getName, param.name());
                change = true;
            }
            if (!Objects.equals(sysRole.getRoleCode(), param.roleCode())) {
                condition.or().eq(SysRole::getRoleCode, param.roleCode());
                change = true;
            }
            if (change) {
                Assert.state(!condition.exists(), "角色名称或角色编码已存在");
            }
        } else {
            sysRole = lambdaQuery()
                    .eq(SysRole::getName, param.name())
                    .or()
                    .eq(SysRole::getRoleCode, param.roleCode())
                    .one();
            Assert.isNull(sysRole, "角色名称或角色编码已存在");
            sysRole = new SysRole();
        }
        sysRole.setName(param.name());
        sysRole.setRoleCode(param.roleCode());
        sysRole.setRemark(param.remark());

        saveOrUpdate(sysRole);

        sysRoleMenuService.syncRoleMenu(sysRole.getId(), param.menuIds());

        // 异步同步已登录用户角色权限
        if (update) {
            publisher.publishEvent(new SyncUserRoleAuthorityEvent());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delRole(RoleDelParam param) {
        removeById(param.id());

        // 删除用户 & 角色关系
        sysUserRoleService.lambdaUpdate()
                .eq(SysUserRole::getRoleId, param.id())
                .remove();

        // 删除角色 & 菜单关系
        sysRoleMenuService.lambdaUpdate()
                .eq(SysRoleMenu::getRoleId, param.id())
                .remove();

        // 异步同步已登录用户角色权限
        publisher.publishEvent(new SyncUserRoleAuthorityEvent());
    }

    @Override
    public BasePageResult<SysRoleResult> rolePage(RoleListQueryParam param) {
        Page<SysRole> pageResult = lambdaQuery()
                .page(new Page<>(param.getCurrent(), param.getPageSize()));

        //return BasePageResult.ofPage(pageResult,
        //        () -> pageResult.getRecords()
        //                .stream()
        //                .map(item -> BeanUtil.copyProperties(item, SysRoleResult.class))
        //                .toList()
        //);
        return new BasePageResult<>(pageResult.convert(item -> BeanUtil.copyProperties(item, SysRoleResult.class)));
    }

    @Override
    public SysRoleInfoResult roleInfo(RoleInfoQueryParam param) {
        SysRole sysRole = getById(param.id());
        if (sysRole == null) {
            throw BizException.valueOfMsg("角色不存在");
        }
        SysRoleInfoResult sysRoleResult = BeanUtil.copyProperties(sysRole, SysRoleInfoResult.class);
        // 查询出没有子节点的 menuId 即最小一级的 menu
        sysRoleResult.setMenuIds(sysRoleMenuService.getLowestMenuIdsByRoleId(sysRole.getId()));
        return sysRoleResult;
    }

    @Override
    public List<SysRoleResult> roleList(RoleListQueryParam param) {
        return list().stream().map(role -> BeanUtil.toBean(role, SysRoleResult.class)).toList();
    }
}
