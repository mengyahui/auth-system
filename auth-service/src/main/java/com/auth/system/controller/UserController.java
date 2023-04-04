package com.auth.system.controller;


import com.auth.model.dto.ChangeStatusDto;
import com.auth.model.dto.user.AddUserDto;
import com.auth.model.dto.user.AssignUserDto;
import com.auth.model.dto.user.SearchUserDto;
import com.auth.model.dto.user.UpdateUserDto;
import com.auth.model.result.Result;
import com.auth.model.vo.PageVo;
import com.auth.model.vo.role.RoleVo;
import com.auth.model.vo.user.UserVo;
import com.auth.system.service.RoleService;
import com.auth.system.service.UserService;
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
 * @time 2023/03/30 上午 08:54
 */
@RestController
@RequestMapping("system/user")
@Api(tags = "用户管理")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @ApiOperation("根据查询条件获取用户列表")
    @PostMapping("list")
    public Result<?> getUserList(@RequestBody SearchUserDto searchUserDto) {
        PageVo pageVo = userService.getUserList(searchUserDto);
        return Result.ok(pageVo);
    }

    @ApiOperation("删除用户")
    @DeleteMapping("delete")
    public Result<?> deleteUser(@RequestBody @ApiParam("用户id列表") List<Long> userIds) {
        int result = userService.removeUserByIds(userIds);
        return result>0 ? Result.ok(): Result.fail();
    }

    @ApiOperation("添加用户")
    @PostMapping("add")
    public Result<?> addUser(@RequestBody AddUserDto addUserDto) {
        int result = userService.insertUser(addUserDto);
        return result>0 ? Result.ok(): Result.fail();
    }

    @ApiOperation("修改用户")
    @PutMapping("update")
    public Result<?> updateUser(@RequestBody UpdateUserDto updateUserDto) {
        int result = userService.editUserById(updateUserDto);
        return result>0 ? Result.ok(): Result.fail();
    }

    @ApiOperation("通过id获取用户")
    @GetMapping("{id}")
    public Result<?> getUserById(@PathVariable @ApiParam("用户id") Long id) {
        UserVo userVo = userService.selectUserById(id);
        return Result.ok(userVo);
    }

    @ApiOperation("导出用户")
    @PostMapping("download")
    public void download(HttpServletResponse response,@RequestBody @ApiParam("用户id列表") List<Long> userIds) throws IOException {
        userService.downLoad(response,userIds);
    }

    @ApiOperation("更改用户状态")
    @PutMapping("status")
    public Result<?> changeUserStatus(@RequestBody ChangeStatusDto statusDto) {
       int result = userService.changeStatusById(statusDto);
        return result>0 ? Result.ok(): Result.fail();
    }

    @ApiOperation("获取用户具有的角色")
    @GetMapping("assign/{id}")
    public Result<?> assign(@PathVariable @ApiParam("用户id") Long id) {
        List<RoleVo> userRoles = roleService.selectRoleSByUserId(id);
        return Result.ok(userRoles);
    }

    @ApiOperation("给用户分配角色")
    @PutMapping("toAssign")
    public Result<?> toAssign(@RequestBody AssignUserDto assignUserDto) {
        boolean assign = userService.toAssign(assignUserDto);
        return assign ? Result.ok() : Result.fail();
    }

}
