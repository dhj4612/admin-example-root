package org.example.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.example.admin.model.entity.SysMenu;

import java.util.Set;

/**
 * 菜单管理
 *
 */
public interface SysMenuService extends IService<SysMenu> {

    Set<String> getUserAuthsByUsrId(@Param("id") Integer id);
}
