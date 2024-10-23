package org.example.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.admin.model.entity.SysRole;
import org.example.admin.model.param.RoleAddOrUpdateParam;
import org.example.admin.model.param.RoleDelParam;
import org.example.admin.model.param.RoleInfoQueryParam;
import org.example.admin.model.param.RoleListQueryParam;
import org.example.admin.model.result.SysRoleInfoResult;
import org.example.admin.model.result.SysRoleResult;
import org.example.framework.common.base.BasePageResult;

import java.util.List;

/**
 * 角色
 *
 */
public interface SysRoleService extends IService<SysRole> {

    void addOrUpdateRole(RoleAddOrUpdateParam param);

    void delRole(RoleDelParam param);

    BasePageResult<SysRoleResult> rolePage(RoleListQueryParam param);

    SysRoleInfoResult roleInfo(RoleInfoQueryParam param);

    List<SysRoleResult> roleList(RoleListQueryParam param);
}
