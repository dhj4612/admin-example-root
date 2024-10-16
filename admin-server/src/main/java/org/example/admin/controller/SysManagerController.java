package org.example.admin.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.admin.model.param.MenuAddOrUpdateParam;
import org.example.admin.model.param.RoleAddOrUpdateParam;
import org.example.admin.service.SysMenuService;
import org.example.admin.service.SysRoleService;
import org.example.framework.common.base.Result;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys/manager")
@RequiredArgsConstructor
public class SysManagerController {
    private final SysMenuService sysMenuService;
    private final SysRoleService sysRoleService;

    @PostMapping("/role-add-or-update")
    @PreAuthorize("hasAnyAuthority('sys:manager:role:add','sys:manager:role:update')")
    public Result<Void> addOrUpdateRole(@RequestBody @Valid RoleAddOrUpdateParam param) {
        sysRoleService.addOrUpdateRole(param);
        return Result.ok();
    }

    @PostMapping("/menu-add-or-update")
    @PreAuthorize("hasAnyAuthority('sys:manager:menu:add','sys:manager:menu:update')")
    public Result<Void> addOrUpdateMenu(@RequestBody @Valid MenuAddOrUpdateParam param) {
        sysMenuService.addOrUpdateMenu(param);
        return Result.ok();
    }
}
