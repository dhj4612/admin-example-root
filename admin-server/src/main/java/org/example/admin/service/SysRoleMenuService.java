package org.example.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.validation.constraints.NotEmpty;
import org.example.admin.model.entity.SysRoleMenu;

import java.util.Set;

/**
 * 角色与菜单对应关系
 *
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {

    void syncRoleMenu(Integer id, @NotEmpty(message = "权限列表不能为空") Set<Integer> menuIds);
}
