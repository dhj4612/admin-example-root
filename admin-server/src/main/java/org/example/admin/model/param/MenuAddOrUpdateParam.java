package org.example.admin.model.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MenuAddOrUpdateParam(
        Integer id,
        Integer pid,
        @NotBlank(message = "菜单名称不能为空") String name,
        String url,
        String authority,
        @NotNull(message = "菜单类型不能为空") Integer type,
        @NotNull(message = "打开方式不能为空") Integer openStyle,
        Integer sort) {
}
