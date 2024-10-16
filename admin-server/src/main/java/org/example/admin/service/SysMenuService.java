package org.example.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.validation.Valid;
import org.apache.ibatis.annotations.Param;
import org.example.admin.model.entity.SysMenu;
import org.example.admin.model.param.MenuAddOrUpdateParam;

import java.util.Set;

/**
 * 菜单管理
 *
 */
public interface SysMenuService extends IService<SysMenu> {

    Set<String> getUserAuthsByUsrId(@Param("id") Integer id);

    void addOrUpdateMenu(MenuAddOrUpdateParam param);
}
