package org.example.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.example.admin.mapper.SysRoleMapper;
import org.example.admin.model.entity.SysRole;
import org.example.admin.model.entity.SysUserRole;
import org.example.admin.model.event.SyncUserRoleAuthorityEvent;
import org.example.admin.model.param.RoleAddOrUpdateParam;
import org.example.admin.model.param.RoleDelParam;
import org.example.admin.service.SysRoleMenuService;
import org.example.admin.service.SysRoleService;
import org.example.admin.service.SysUserRoleService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.awt.desktop.SystemSleepEvent;
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

            if (!Objects.equals(sysRole.getName(), param.name())
                    || !Objects.equals(sysRole.getRoleCode(), param.code())) {
                Assert.isNull(lambdaQuery()
                        .eq(SysRole::getName, param.name())
                        .or()
                        .eq(SysRole::getRoleCode, param.code())
                        .one(), "角色名称或角色编码已存在");
            }
        } else {
            sysRole = lambdaQuery()
                    .eq(SysRole::getName, param.name())
                    .or()
                    .eq(SysRole::getRoleCode, param.code())
                    .one();
            Assert.isNull(sysRole, "角色名称或角色编码已存在");
            sysRole = new SysRole();
        }
        sysRole.setName(param.name());
        sysRole.setRoleCode(param.code());
        sysRole.setRemark(param.remark());

        saveOrUpdate(sysRole);

        sysRoleMenuService.syncRoleMenu(sysRole.getId(), param.menuIds());

        // 异步同步已登录用户角色权限
        publisher.publishEvent(new SyncUserRoleAuthorityEvent());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delRole(RoleDelParam param) {
        removeById(param.id());

        // 更新用户 & 角色关系
        sysUserRoleService.lambdaUpdate()
                .eq(SysUserRole::getRoleId, param.id())
                .remove();

        // 异步同步已登录用户角色权限
        publisher.publishEvent(new SyncUserRoleAuthorityEvent());
    }
}
