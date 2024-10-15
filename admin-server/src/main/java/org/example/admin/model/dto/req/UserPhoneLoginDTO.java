package org.example.admin.model.dto.req;

import jakarta.validation.constraints.NotBlank;

public record UserPhoneLoginDTO(@NotBlank(message = "手机号不能为空") String phone,
                                @NotBlank(message = "密码不能为空") String password) {
}
