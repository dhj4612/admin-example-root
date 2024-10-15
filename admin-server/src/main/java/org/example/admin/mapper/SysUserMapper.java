package org.example.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.admin.model.entity.SysUser;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
}
