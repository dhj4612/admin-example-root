package org.example.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.example.admin.mapper.SysRoleMapper;
import org.example.admin.model.entity.SysRole;
import org.example.admin.model.param.RoleAddOrUpdateParam;
import org.example.admin.service.SysRoleMenuService;
import org.example.admin.service.SysRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * 角色
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    private final SysRoleMenuService sysRoleMenuService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdateRole(RoleAddOrUpdateParam param) {
        final boolean update = param.id() != null;
        SysRole sysRole = lambdaQuery()
                .eq(SysRole::getName, param.name())
                .or()
                .eq(SysRole::getRoleCode, param.code())
                .one();

        Assert.isNull(sysRole, "角色名称或角色编码已存在");

        if (update) {
            sysRole = getById(param.id());
            Assert.notNull(sysRole, "角色不存在或已禁用");
        } else {
            sysRole = new SysRole();
        }
        sysRole.setName(param.name());
        sysRole.setRoleCode(param.code());
        sysRole.setRemark(param.remark());

        saveOrUpdate(sysRole);

        sysRoleMenuService.saveOrUpdateRoleMenu(sysRole.getId(), param.menuIds());
    }
}
