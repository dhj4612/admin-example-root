package org.example.admin.model.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record RoleAddOrUpdateParam(
        Integer id,
        @NotBlank(message = "角色名称不能为空") String name,
        @NotBlank(message = "角色编码不能为空") String roleCode,
        String remark,
        @NotNull(message = "权限列表不能为空") Set<Integer> menuIds) {
}
