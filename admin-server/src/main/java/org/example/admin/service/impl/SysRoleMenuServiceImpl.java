package org.example.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.example.admin.mapper.SysRoleMenuMapper;
import org.example.admin.model.entity.SysRoleMenu;
import org.example.admin.service.SysRoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 角色与菜单对应关系
 *
 */
@Service
@RequiredArgsConstructor
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncRoleMenu(Integer id, Set<Integer> menuIds) {
        List<Integer> roleMenuIds = lambdaQuery()
                .eq(SysRoleMenu::getRoleId, id)
                .list()
                .stream().map(SysRoleMenu::getMenuId).toList();

        Collection<Integer> saveMenuIds = CollUtil.subtract(menuIds, roleMenuIds);

        List<SysRoleMenu> roleMenuList = saveMenuIds.stream()
                .map(menuId -> new SysRoleMenu().setMenuId(menuId).setRoleId(id))
                .toList();
        saveBatch(roleMenuList);

        lambdaUpdate()
                .eq(SysRoleMenu::getRoleId, id)
                .in(SysRoleMenu::getMenuId, CollUtil.subtract(roleMenuIds, menuIds))
                .remove();
    }
}
