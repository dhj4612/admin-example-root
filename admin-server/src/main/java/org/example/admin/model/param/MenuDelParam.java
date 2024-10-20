package org.example.admin.model.param;

import jakarta.validation.constraints.NotNull;

public record MenuDelParam(@NotNull(message = "id不能为空") Integer id) {
}
