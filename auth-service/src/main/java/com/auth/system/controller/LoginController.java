package com.auth.system.controller;


import com.auth.exception.SystemException;
import com.auth.helper.JwtHelper;
import com.auth.helper.RedisHelper;
import com.auth.model.dto.user.UserLoginDto;
import com.auth.model.entity.LoginUser;
import com.auth.model.result.ApiResult;
import com.auth.model.result.StatusEnum;
import com.auth.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private RedisHelper redisHelper;



    @PostMapping("/login")
    @ApiOperation("用户登录")
    public ApiResult<?> login(@RequestBody UserLoginDto userLoginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userLoginDto.getUsername() , userLoginDto.getPassword()) ;

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        if(authentication == null) {
            throw new SystemException(StatusEnum.ACCOUNT_PASSWORD_ERROR);
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userId = loginUser.getUser().getId().toString();

        redisHelper.setCacheObject("login_user:" + userId,loginUser);

        String token = JwtHelper.createToken(loginUser.getUser().getId().toString(), loginUser.getUser().getUsername());
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        return ApiResult.success(map);
    }


    @GetMapping("/info")
    @ApiOperation("获取用户信息")
    public ApiResult<?> info(HttpServletRequest request) {
        String token = request.getHeader("token");
        String username = JwtHelper.getUsername(token);
        Map<String, Object> map = userService.getUserInfo(username);
        return ApiResult.success(map);
    }


    @PostMapping("/logout")
    @ApiOperation("退出登录")
    public ApiResult<?> logout(){
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = loginUser.getUser().getId();
        redisHelper.del("login_user:" + userId);
        return ApiResult.success();
    }

}
