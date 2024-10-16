package org.example.admin.service.impl;


import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.example.admin.mapper.SysUserRoleMapper;
import org.example.admin.model.entity.SysUser;
import org.example.admin.model.entity.SysUserRole;
import org.example.admin.service.SysUserRoleService;
import org.example.framework.common.exception.BizException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 用户角色关系
 *
 */
@Service
@RequiredArgsConstructor
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateUserRoleRelation(SysUser sysUser, Set<Integer> roleIds) {
        if (CollUtil.isEmpty(roleIds)) {
            throw BizException.valueOfMsg("角色列表不能为空");
        }
        final Integer userId = sysUser.getId();
        List<Integer> userRoleIds = lambdaQuery()
                .eq(SysUserRole::getUserId, userId)
                .list().stream().map(SysUserRole::getRoleId).toList();

        // 需要新增的角色id集合
        Collection<Integer> saveRoleIds = CollUtil.subtract(roleIds, userRoleIds);
        // 建立新的关联关系
        List<SysUserRole> userRoleList = saveRoleIds.stream().map(id -> new SysUserRole().setRoleId(id).setUserId(userId))
                .toList();
        saveBatch(userRoleList);

        // 查出旧的关联关系删除
        lambdaUpdate()
                .eq(SysUserRole::getUserId, sysUser.getId())
                .in(SysUserRole::getRoleId, CollUtil.subtract(userRoleIds, roleIds))
                .remove();
    }

    @Override
    public Set<String> getUserRolesByUserId(Integer id) {
        return baseMapper.selectRoleListByUserId(id);
    }
}
