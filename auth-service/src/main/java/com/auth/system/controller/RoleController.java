package com.auth.system.controller;


import com.auth.model.dto.role.AddRoleDto;
import com.auth.model.dto.role.AssignRoleDto;
import com.auth.model.dto.role.SearchRoleDto;
import com.auth.model.entity.Role;
import com.auth.model.result.ApiResult;
import com.auth.model.vo.PageVo;
import com.auth.model.vo.role.RoleVo;
import com.auth.system.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

/**
 * @author MYH
 * @time 2023/03/30 上午 08:55
 */
@RestController
@RequestMapping("system/role")
@Api(tags = "角色管理")
@Validated
public class RoleController {
    
    @Resource
    private RoleService roleService;
    
    @ApiOperation("获取全部角色")
    @GetMapping("all")
    public ApiResult<?> findAllRole() {
        List<RoleVo> allRole = roleService.findAllRole();
        return ApiResult.success(allRole);
    }

    @ApiOperation("根据条件查询角色列表")
    @PostMapping("list")
    public ApiResult<?> getRoleList(@RequestBody SearchRoleDto searchRoleDto) {
        PageVo pageVo = roleService.getRoleList(searchRoleDto);
        return ApiResult.success(pageVo);
    }

    @ApiOperation("根据角色id查询角色")
    @GetMapping("{id}")
    public ApiResult<?> getRoleById(@NotNull(message = "角色id不能为空") @PathVariable @ApiParam("角色id") Long id) {
        Role role = roleService.getRoleById(id);
        return ApiResult.success(role);
    }

    @ApiOperation("添加角色")
    @PostMapping("add")
    public ApiResult<?> addRole(@Validated @RequestBody AddRoleDto addRoleDto) {
        int result = roleService.addRole(addRoleDto);
        return result > 0 ? ApiResult.success() : ApiResult.fail();
    }

    @ApiOperation("修改角色")
    @PutMapping("update")
    public ApiResult<?> updateRole(@Validated @RequestBody Role role) {
        int result = roleService.updateRole(role);
        return result > 0 ? ApiResult.success() : ApiResult.fail();
    }

    @ApiOperation("删除角色")
    @DeleteMapping("delete")
    public ApiResult<?> deleteRole(@NotEmpty(message = "角色Id列表不能为空") @RequestBody @ApiParam("角色id列表") List<Long> roleIds) {
        int result = roleService.deleteRole(roleIds);
        return result > 0 ? ApiResult.success() : ApiResult.fail();
    }

    @ApiOperation("导出角色")
    @PutMapping("download")
    public void export(HttpServletResponse response, @NotEmpty(message = "角色Id列表不能为空") @RequestBody @ApiParam("角色id列表") List<Long> roleIds) throws IOException {
        roleService.exportData(response,roleIds);
    }

    @ApiOperation("给角色分配权限")
    @PutMapping("toAssign")
    public ApiResult<?> toAssign(@RequestBody AssignRoleDto assignRoleDto) {
        boolean result = roleService.doAssign(assignRoleDto);
        return result ? ApiResult.success() : ApiResult.fail();
    }

}
