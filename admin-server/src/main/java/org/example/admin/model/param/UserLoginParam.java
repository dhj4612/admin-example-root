package org.example.admin.model.param;

import jakarta.validation.constraints.NotBlank;

public record UserLoginParam(@NotBlank(message = "手机号不能为空") String account,
                             @NotBlank(message = "密码不能为空") String password) {
}
