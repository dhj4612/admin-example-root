package org.example.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.admin.model.entity.SysRoleMenu;

import java.util.Set;

@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {
    Set<Integer> selectLowestMenuIdsByRoleId(@Param("roleId") Integer roleId);
}
