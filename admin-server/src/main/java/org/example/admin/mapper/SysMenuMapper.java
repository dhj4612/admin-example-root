package org.example.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.admin.model.entity.SysMenu;

import java.util.Set;

@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    Set<String> selectAuthorityListByUserId(@Param("id") Integer id);
}
