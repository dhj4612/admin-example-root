package org.example.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.validation.Valid;
import org.example.admin.model.dto.req.UserPhoneLoginDTO;
import org.example.admin.model.dto.req.UserPhoneRegisterDTO;
import org.example.admin.model.dto.resp.UserLoginRespDTO;
import org.example.admin.model.entity.SysUser;

/**
 * 用户管理
 */
public interface SysUserService extends IService<SysUser> {

    void phoneRegister(@Valid UserPhoneRegisterDTO param);

    UserLoginRespDTO phoneLogin(@Valid UserPhoneLoginDTO param);
}
