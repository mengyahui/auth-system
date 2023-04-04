package com.auth.system.controller;


import com.auth.model.dto.role.AddRoleDto;
import com.auth.model.dto.role.AssignRoleDto;
import com.auth.model.dto.role.SearchRoleDto;
import com.auth.model.entity.Role;
import com.auth.model.result.Result;
import com.auth.model.vo.PageVo;
import com.auth.model.vo.role.RoleVo;
import com.auth.system.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author MYH
 * @time 2023/03/30 上午 08:55
 */
@RestController
@RequestMapping("system/role")
@Api(tags = "角色管理")
public class RoleController {
    
    @Resource
    private RoleService roleService;
    
    @ApiOperation("获取全部角色")
    @GetMapping("all")
    public Result<?> findAllRole() {
        List<RoleVo> allRole = roleService.findAllRole();
        return Result.ok(allRole);
    }

    @ApiOperation("根据条件查询角色列表")
    @PostMapping("list")
    public Result<?> getRoleList(@RequestBody SearchRoleDto searchRoleDto) {
        PageVo pageVo = roleService.getRoleList(searchRoleDto);
        return Result.ok(pageVo);
    }

    @ApiOperation("根据角色id查询角色")
    @GetMapping("{id}")
    public Result<?> getRoleById(@PathVariable @ApiParam("角色id") Long id) {
        Role role = roleService.getRoleById(id);
        return Result.ok(role);
    }

    @ApiOperation("添加角色")
    @PostMapping("add")
    public Result<?> addRole(@RequestBody AddRoleDto addRoleDto) {
        int result = roleService.addRole(addRoleDto);
        return result > 0 ? Result.ok() : Result.fail();
    }

    @ApiOperation("修改角色")
    @PutMapping("update")
    public Result<?> updateRole(@RequestBody Role role) {
        int result = roleService.updateRole(role);
        return result > 0 ? Result.ok() : Result.fail();
    }

    @ApiOperation("删除角色")
    @DeleteMapping("delete")
    public Result<?> deleteRole(@RequestBody @ApiParam("角色id列表") List<Long> roleIds) {
        int result = roleService.deleteRole(roleIds);
        return result > 0 ? Result.ok() : Result.fail();
    }

    @ApiOperation("导出角色")
    @PutMapping("download")
    public void export(HttpServletResponse response, @RequestBody @ApiParam("角色id列表") List<Long> roleIds) throws IOException {
        roleService.exportData(response,roleIds);
    }

    @ApiOperation("给角色分配权限")
    @PutMapping("toAssign")
    public Result<?> toAssign(@RequestBody AssignRoleDto assignRoleDto) {
        boolean result = roleService.doAssign(assignRoleDto);
        return result ? Result.ok() : Result.fail();
    }

}
