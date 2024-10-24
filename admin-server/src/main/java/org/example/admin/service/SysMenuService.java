package org.example.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.admin.model.entity.SysMenu;
import org.example.admin.model.param.MenuAddOrUpdateParam;
import org.example.admin.model.param.MenuDelParam;
import org.example.admin.model.result.SysMenuResult;
import org.example.framework.security.core.user.UserAuthorized;

import java.util.List;

/**
 * 菜单管理
 *
 */
public interface SysMenuService extends IService<SysMenu> {
    void addOrUpdateMenu(MenuAddOrUpdateParam param);

    List<SysMenu> getUserMenuList(UserAuthorized user, Integer type);

    List<SysMenuResult> geMenuList(Integer type);

    void delMenu(MenuDelParam param);
}
