package org.example.admin.model.param;

import jakarta.validation.constraints.NotBlank;

public record UserPhoneLoginParam(@NotBlank(message = "手机号不能为空") String phone,
                                  @NotBlank(message = "密码不能为空") String password) {
}
