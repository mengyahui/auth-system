package com.auth.system.controller;


import com.alibaba.fastjson.JSON;
import com.auth.helper.JwtHelper;
import com.auth.model.dto.user.UserLoginDto;
import com.auth.model.entity.LoginUser;
import com.auth.model.entity.User;
import com.auth.model.result.Result;
import com.auth.system.service.UserService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Resource
    private AuthenticationManager authenticationManager ;

    @Resource
    private RedisTemplate<String , String> redisTemplate ;



    @PostMapping("/login")
    @ApiOperation("用户登录")
    public Result<?> login(@RequestBody UserLoginDto userLoginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userLoginDto.getUsername() , userLoginDto.getPassword()) ;

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        if(authentication == null) {
            throw new RuntimeException("用户名或密码错误");
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        redisTemplate.boundValueOps("login_user:" + userId).set(JSON.toJSONString(loginUser));

        String token = JwtHelper.createToken(loginUser.getUser().getId().toString(), loginUser.getUser().getUsername());
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        return Result.ok(map);
    }


    @GetMapping("/info")
    @ApiOperation("获取用户信息")
    public Result<?> info(HttpServletRequest request) {
        String token = request.getHeader("token");
        String username = JwtHelper.getUsername(token);
        Map<String, Object> map = userService.getUserInfo(username);
        return Result.ok(map);
    }


    @PostMapping("/logout")
    @ApiOperation("退出登录")
    public Result<?> logout(){
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = loginUser.getUser().getId();
        redisTemplate.delete("login_user:" + userId);
        return Result.ok();
    }

}
