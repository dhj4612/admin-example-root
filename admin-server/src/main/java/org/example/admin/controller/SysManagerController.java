package org.example.admin.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.admin.model.param.MenuAddOrUpdateParam;
import org.example.admin.model.param.RoleAddOrUpdateParam;
import org.example.admin.model.result.SysMenuResult;
import org.example.admin.service.SysMenuService;
import org.example.admin.service.SysRoleService;
import org.example.framework.common.base.Result;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sys/manager")
@RequiredArgsConstructor
public class SysManagerController {
    private final SysMenuService sysMenuService;
    private final SysRoleService sysRoleService;

    @PostMapping("/role-add-or-update")
    @PreAuthorize("hasAnyAuthority('sys:role:add','sys:role:update')")
    public Result<Void> addOrUpdateRole(@RequestBody @Valid RoleAddOrUpdateParam param) {
        sysRoleService.addOrUpdateRole(param);
        return Result.ok();
    }

    @PostMapping("/menu-add-or-update")
    @PreAuthorize("hasAnyAuthority('sys:menu:add','sys:menu:update')")
    public Result<Void> addOrUpdateMenu(@RequestBody @Valid MenuAddOrUpdateParam param) {
        sysMenuService.addOrUpdateMenu(param);
        return Result.ok();
    }

    @GetMapping("/menu/list")
    @PreAuthorize("hasAnyRole('admin')")
    public Result<List<SysMenuResult>> menuList(@RequestParam(value = "type", required = false) Integer type) {
        return Result.ok(sysMenuService.geMenuList(type));
    }
}
