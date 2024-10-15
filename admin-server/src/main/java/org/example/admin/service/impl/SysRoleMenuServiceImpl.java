package org.example.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.example.admin.mapper.SysRoleMenuMapper;
import org.example.admin.model.entity.SysRoleMenu;
import org.example.admin.service.SysRoleMenuService;
import org.springframework.stereotype.Service;



/**
 * 角色与菜单对应关系
 *
 */
@Service
@RequiredArgsConstructor
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {



}
