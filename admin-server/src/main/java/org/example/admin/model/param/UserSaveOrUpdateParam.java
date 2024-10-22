package org.example.admin.model.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record UserSaveOrUpdateParam(
        Integer id,
        @NotBlank(message = "手机号不能为空") String phone,
        @NotBlank(message = "密码不能为空") String password,
        @NotBlank(message = "用户名不能为空") String username,
        @NotNull(message = "角色列表不能为空") Set<Integer> roleIds) {
}
