package org.example.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.example.admin.mapper.SysMenuMapper;
import org.example.admin.model.entity.SysMenu;
import org.example.admin.model.param.MenuAddOrUpdateParam;
import org.example.admin.service.SysMenuService;
import org.example.framework.security.core.user.UserAuthorized;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Set;


/**
 * 菜单管理
 *
 */
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Override
    public Set<String> getUserAuthsByUsrId(Integer id) {
        return baseMapper.selectAuthorityListByUserId(id);
    }

    @Override
    public void addOrUpdateMenu(MenuAddOrUpdateParam param) {
        final boolean update = param.id() != null;
        SysMenu menu = lambdaQuery().eq(SysMenu::getName, param.name())
                .or()
                .eq(SysMenu::getAuthority, param.authority())
                .one();
        Assert.isNull(menu, "菜单名称或授权标识已存在");

        if (update) {
            menu = getById(param.id());
            Assert.notNull(menu, "菜单不存在");
        } else {
            menu = new SysMenu();
        }

        menu.setName(param.name());
        menu.setAuthority(param.authority());
        menu.setOpenStyle(param.openStyle());
        menu.setSort(param.sort() == null ? 0 : param.sort());
        menu.setType(param.type());
        menu.setUrl(param.url());
        menu.setPid(param.pid());

        saveOrUpdate(menu);
    }

    @Override
    public List<SysMenu> getUserMenuList(UserAuthorized user, Integer type) {
        return baseMapper.selectMenuListByUserAndType(user, type);
    }
}
