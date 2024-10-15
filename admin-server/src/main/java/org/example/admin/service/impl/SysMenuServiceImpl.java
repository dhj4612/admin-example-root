package org.example.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.example.admin.mapper.SysMenuMapper;
import org.example.admin.model.entity.SysMenu;
import org.example.admin.service.SysMenuService;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
        Set<String> authoritySet = baseMapper.selectAuthorityListByUserId(id);
        return CollUtil.isEmpty(authoritySet) ? Collections.emptySet() : authoritySet;
    }
}
