package com.auth.system.controller;


import com.auth.model.dto.ChangeStatusDto;
import com.auth.model.dto.user.AddUserDto;
import com.auth.model.dto.user.AssignUserDto;
import com.auth.model.dto.user.SearchUserDto;
import com.auth.model.dto.user.UpdateUserDto;
import com.auth.model.result.ApiResult;
import com.auth.model.vo.PageVo;
import com.auth.model.vo.role.RoleVo;
import com.auth.model.vo.user.UserVo;
import com.auth.system.service.RoleService;
import com.auth.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

/**
 * @author MYH
 * @time 2023/03/30 上午 08:54
 */
@RestController
@RequestMapping("system/user")
@Api(tags = "用户管理")
@Validated
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @ApiOperation("根据查询条件获取用户列表")
    @PostMapping("list")
    public ApiResult<?> getUserList(@RequestBody SearchUserDto searchUserDto) {
        PageVo pageVo = userService.getUserList(searchUserDto);
        return ApiResult.success(pageVo);
    }

    @ApiOperation("删除用户")
    @DeleteMapping("delete")
    @PreAuthorize("hasAnyAuthority('btn.user.delete')")
    public ApiResult<?> deleteUser(@NotEmpty(message = "用户id列表不能为空") @RequestBody @ApiParam("用户id列表") List<Long> userIds) {
        int result = userService.removeUserByIds(userIds);
        return result>0 ? ApiResult.success(): ApiResult.fail();
    }

    @ApiOperation("添加用户")
    @PostMapping("add")
    public ApiResult<?> addUser(@Validated @RequestBody AddUserDto addUserDto) {
        int result = userService.insertUser(addUserDto);
        return result > 0 ? ApiResult.success(): ApiResult.fail();
    }

    @ApiOperation("修改用户")
    @PutMapping("update")
    public ApiResult<?> updateUser(@Validated @RequestBody UpdateUserDto updateUserDto) {
        int result = userService.editUserById(updateUserDto);
        return result >0 ? ApiResult.success(): ApiResult.fail();
    }

    @ApiOperation("通过id获取用户")
    @GetMapping("{id}")
    public ApiResult<?> getUserById(@NotNull(message = "用户id不能为空") @PathVariable @ApiParam("用户id") Long id) {
        UserVo userVo = userService.selectUserById(id);
        return ApiResult.success(userVo);
    }

    @ApiOperation("导出用户")
    @PostMapping("download")
    public void download(HttpServletResponse response,@RequestBody @ApiParam("用户id列表") List<Long> userIds) throws IOException {
        userService.downLoad(response,userIds);
    }

    @ApiOperation("更改用户状态")
    @PutMapping("status")
    public ApiResult<?> changeUserStatus(@Validated @RequestBody ChangeStatusDto statusDto) {
       int result = userService.changeStatusById(statusDto);
        return result>0 ? ApiResult.success(): ApiResult.fail();
    }

    @ApiOperation("获取用户具有的角色")
    @GetMapping("assign/{id}")
    public ApiResult<?> assign(@NotNull(message = "用户id不能为空") @PathVariable @ApiParam("用户id") Long id) {
        List<RoleVo> userRoles = roleService.selectRoleSByUserId(id);
        return ApiResult.success(userRoles);
    }

    @ApiOperation("给用户分配角色")
    @PutMapping("toAssign")
    public ApiResult<?> toAssign(@Validated @RequestBody AssignUserDto assignUserDto) {
        boolean assign = userService.toAssign(assignUserDto);
        return assign ? ApiResult.success() : ApiResult.fail();
    }

}
