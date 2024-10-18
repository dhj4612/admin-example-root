package org.example.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.admin.model.entity.SysUser;

import java.util.Set;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    Set<String> selectAuthoritiesByUserId(@Param("userId") Integer userId);
}
