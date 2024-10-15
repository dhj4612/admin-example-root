package org.example.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.validation.constraints.NotNull;
import org.apache.ibatis.annotations.Param;
import org.example.admin.model.entity.SysUser;
import org.example.admin.model.entity.SysUserRole;

import java.util.Set;

/**
 * 用户角色关系
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    void saveUserRoleRelation(SysUser sysUser, @NotNull(message = "角色列表不能为空") Set<Integer> roles);

    Set<String> getUserRolesByUserId(@Param("id") Integer id);
}
