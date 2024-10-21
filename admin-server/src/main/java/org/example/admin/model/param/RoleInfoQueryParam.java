package org.example.admin.model.param;

import jakarta.validation.constraints.NotNull;

public record RoleInfoQueryParam(@NotNull(message = "id不存在") Integer id) {
}
