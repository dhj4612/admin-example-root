package org.example.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.example.admin.mapper.SysMenuMapper;
import org.example.admin.model.entity.SysMenu;
import org.example.admin.model.entity.SysRoleMenu;
import org.example.admin.model.event.SyncUserRoleAuthorityEvent;
import org.example.admin.model.param.MenuAddOrUpdateParam;
import org.example.admin.model.param.MenuDelParam;
import org.example.admin.model.result.SysMenuResult;
import org.example.admin.service.SysMenuService;
import org.example.admin.service.SysRoleMenuService;
import org.example.framework.common.exception.BizException;
import org.example.framework.security.core.user.UserAuthorized;
import org.example.framework.utils.TreeUtil;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;

/**
 * 菜单管理
 *
 */
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    private final SysRoleMenuService sysRoleMenuService;
    private final ApplicationEventPublisher publisher;

    @Override
    public void addOrUpdateMenu(MenuAddOrUpdateParam param) {
        final boolean update = param.id() != null;
        SysMenu menu;
        Assert.state(!Objects.equals(param.pid(), param.id()), "上级菜单不能为自身");

        if (update) {
            menu = getById(param.id());
            Assert.notNull(menu, "菜单不存在");

            if (!Objects.equals(menu.getAuthority(), param.authority())) {
                Assert.state(!lambdaQuery()
                                .eq(SysMenu::getAuthority, param.authority()).exists(),
                        "授权标识已存在");
            }
        } else {
            Assert.state(!lambdaQuery().eq(SysMenu::getAuthority, param.authority()).exists(), "授权标识已存在");
            menu = new SysMenu();
        }

        menu.setName(param.name());
        menu.setAuthority(param.authority());
        menu.setOpenStyle(param.openStyle());
        menu.setSort(param.sort() == null ? 0 : param.sort());
        menu.setType(param.type());
        menu.setIcon(param.icon());
        menu.setUrl(param.url());
        menu.setPid(param.pid() == null ? 0 : param.pid());

        saveOrUpdate(menu);
    }

    @Override
    public List<SysMenu> getUserMenuList(UserAuthorized user, Integer type) {
        return baseMapper.selectMenuListByUserAndType(user, type);
    }

    @Override
    public List<SysMenuResult> geMenuList(Integer type) {
        List<SysMenuResult> menuResultList = lambdaQuery()
                .eq(type != null, SysMenu::getType, type)
                .orderByAsc(SysMenu::getSort)
                .list()
                .stream()
                .map(item -> BeanUtil.toBean(item, SysMenuResult.class))
                .toList();
        return TreeUtil.build(menuResultList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delMenu(MenuDelParam param) {
        if (lambdaQuery().eq(SysMenu::getPid, param.id()).exists()) {
            throw BizException.valueOfMsg("请先删除子菜单");
        }
        removeById(param.id());

        // 删除角色 & 菜单关系
        sysRoleMenuService.lambdaUpdate()
                .eq(SysRoleMenu::getMenuId, param.id())
                .remove();

        publisher.publishEvent(new SyncUserRoleAuthorityEvent());
    }
}
