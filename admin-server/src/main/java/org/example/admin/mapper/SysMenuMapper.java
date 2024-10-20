package org.example.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.admin.model.entity.SysMenu;
import org.example.framework.security.core.user.UserAuthorized;

import java.util.List;

@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    List<SysMenu> selectMenuListByUserAndType(@Param("user") UserAuthorized user, @Param("type") Integer type);
}
