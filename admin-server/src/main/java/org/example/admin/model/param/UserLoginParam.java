package org.example.admin.model.param;

import jakarta.validation.constraints.NotBlank;

/**
 * 用户登录参数
 * @param account 账号
 * @param password 密码
 */
public record UserLoginParam(@NotBlank(message = "手机号不能为空") String account,
                             @NotBlank(message = "密码不能为空") String password) {
}
