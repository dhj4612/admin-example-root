package org.example.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.example.admin.mapper.SysRoleMapper;
import org.example.admin.model.entity.SysRole;
import org.example.admin.service.SysRoleService;
import org.springframework.stereotype.Service;

/**
 * 角色
 *
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {


}
