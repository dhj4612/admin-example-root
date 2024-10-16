package org.example.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.validation.Valid;
import org.example.admin.model.entity.SysRole;
import org.example.admin.model.param.RoleAddOrUpdateParam;

/**
 * 角色
 *
 */
public interface SysRoleService extends IService<SysRole> {

    void addOrUpdateRole(RoleAddOrUpdateParam param);
}
