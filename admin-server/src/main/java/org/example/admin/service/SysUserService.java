package org.example.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.validation.Valid;
import org.example.admin.model.param.UserPhoneLoginParam;
import org.example.admin.model.param.UserSaveOrUpdateParam;
import org.example.admin.model.result.SysMenuResult;
import org.example.admin.model.result.UserInfoResult;
import org.example.admin.model.result.UserLoginResult;
import org.example.admin.model.entity.SysUser;

import java.util.List;
import java.util.Set;

/**
 * 用户管理
 */
public interface SysUserService extends IService<SysUser> {

    void userSaveOrUpdate(@Valid UserSaveOrUpdateParam param);

    UserLoginResult phoneLogin(@Valid UserPhoneLoginParam param);

    UserInfoResult getUserInfo();

    Set<String> getUserAuthorities();

    List<SysMenuResult> getUserMenuNav();

    void logout();
}
