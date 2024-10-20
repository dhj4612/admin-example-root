package org.example.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.admin.model.param.*;
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
    @PreAuthorize("hasRole('admin')")
    public Result<Void> addOrUpdateRole(@RequestBody @Valid RoleAddOrUpdateParam param) {
        sysRoleService.addOrUpdateRole(param);
        return Result.ok();
    }

    @PostMapping("/menu-add-or-update")
    @PreAuthorize("hasRole('admin')")
    public Result<Void> addOrUpdateMenu(@RequestBody @Valid MenuAddOrUpdateParam param) {
        sysMenuService.addOrUpdateMenu(param);
        return Result.ok();
    }

    @GetMapping("/menu/list")
    @PreAuthorize("hasAnyRole('admin')")
    public Result<List<SysMenuResult>> menuList(@RequestParam(value = "type", required = false) Integer type) {
        return Result.ok(sysMenuService.geMenuList(type));
    }

    @PostMapping("/menu/info")
    @PreAuthorize("hasRole('admin')")
    public Result<SysMenuResult> menuInfo(@RequestBody @Valid MenuInfoQueryParam param) {
        return Result.ok(BeanUtil.toBean(sysMenuService.getById(param.id()), SysMenuResult.class));
    }

    @PostMapping("/menu/del")
    public Result<Void> menuDel(@RequestBody @Valid MenuDelParam param) {
        sysMenuService.delMenu(param);
        return Result.ok();
    }

    @PostMapping("/role/del")
    public Result<Void> roleDel(@RequestBody @Valid RoleDelParam param) {
        sysRoleService.delRole(param);
        return Result.ok();
    }
}
