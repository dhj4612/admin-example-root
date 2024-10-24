package org.example.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.example.admin.mapper.SysRoleMenuMapper;
import org.example.admin.model.entity.SysMenu;
import org.example.admin.model.entity.SysRoleMenu;
import org.example.admin.service.SysMenuService;
import org.example.admin.service.SysRoleMenuService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色与菜单对应关系
 *
 */
@Service
@RequiredArgsConstructor
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    private final ApplicationContext applicationContext;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncRoleMenu(Integer id, Set<Integer> menuIds) {
        if (menuIds.isEmpty()) {
            lambdaUpdate()
                    .eq(SysRoleMenu::getRoleId, id)
                    .remove();
            return;
        }

        List<Integer> roleMenuIds = lambdaQuery()
                .eq(SysRoleMenu::getRoleId, id)
                .list()
                .stream().map(SysRoleMenu::getMenuId).toList();

        Collection<Integer> saveMenuIds = CollUtil.subtract(menuIds, roleMenuIds);

        List<SysRoleMenu> saveRoleMenuList = saveMenuIds.stream()
                .map(menuId -> new SysRoleMenu().setMenuId(menuId).setRoleId(id))
                .toList();
        saveBatch(saveRoleMenuList);

        Collection<Integer> removeMenuIds = CollUtil.subtract(roleMenuIds, menuIds);
        if (CollUtil.isNotEmpty(removeMenuIds)) {
            lambdaUpdate()
                    .eq(SysRoleMenu::getRoleId, id)
                    .in(SysRoleMenu::getMenuId, removeMenuIds)
                    .remove();
        }
    }

    @Override
    public Set<Integer> getLowestMenuIdsByRoleId(Integer roleId) {
        List<SysRoleMenu> sysRoleMenuList = lambdaQuery().eq(SysRoleMenu::getRoleId, roleId).list();
        List<SysMenu> menus = applicationContext.getBean(SysMenuService.class).lambdaQuery().list();
        return sysRoleMenuList.stream()
                .map(SysRoleMenu::getMenuId)
                .filter(menuId -> menus.stream().noneMatch(item -> Objects.equals(menuId, item.getPid())))
                .collect(Collectors.toSet());
    }
}
