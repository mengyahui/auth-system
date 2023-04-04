package com.auth.system.controller;


import com.auth.model.dto.user.UserLoginDto;
import com.auth.model.result.Result;
import com.auth.system.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MYH
 * @time 2023/03/30 上午 11:27
 */
@RestController
@RequestMapping("/system/login")
@Api(tags = "登录管理")
public class LoginController {

    @Resource
    private UserService userService;


    @PostMapping("/login")
    @ApiOperation("用户登录")
    public Result<?> login(@RequestBody UserLoginDto userLoginDto) {

        Map<String, Object> map = new HashMap<>();
        map.put("token", userLoginDto.getUsername());
        return Result.ok(map);
    }


    @GetMapping("/info")
    @ApiOperation("获取用户信息")
    public Result<?> info(HttpServletRequest request) {
        String token = request.getHeader("X-Token");
        Map<String, Object> map = userService.getUserInfo(token);
        return Result.ok(map);
    }


    @PostMapping("/logout")
    @ApiOperation("退出登录")
    public Result<?> logout(){
        return Result.ok();
    }

}
