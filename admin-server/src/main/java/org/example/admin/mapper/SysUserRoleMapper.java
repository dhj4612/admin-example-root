package org.example.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.admin.model.entity.SysUserRole;

import java.util.Set;

@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    Set<String> selectRoleListByUserId(@Param("id") Integer id);
}
