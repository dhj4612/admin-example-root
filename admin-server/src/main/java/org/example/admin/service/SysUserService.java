package org.example.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.admin.model.entity.SysUser;
import org.example.admin.model.param.*;
import org.example.admin.model.result.*;
import org.example.framework.common.base.BasePageResult;

import java.util.List;
import java.util.Set;

/**
 * 用户管理
 */
public interface SysUserService extends IService<SysUser> {

    void userSaveOrUpdate(UserSaveOrUpdateParam param);

    UserLoginResult phoneLogin(UserPhoneLoginParam param);

    UserInfoResult getUserInfo();

    Set<String> getUserAuthoritiesAndRoles();

    Set<String> getUserAuthoritiesByUserId(Integer id);

    Set<String> getUserRolesByUserId(Integer id);

    List<SysMenuResult> getUserMenuNav();

    void logout();

    BasePageResult<SysUserResult> userPage(UserListQueryParam param);

    SysUserInfoResult getUpdateUserInfo(SysUserInfoQueryParam param);

    void delUser(UserDelParam param);
}
