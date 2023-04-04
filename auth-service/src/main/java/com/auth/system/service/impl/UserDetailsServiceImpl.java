package com.auth.system.service.impl;

import com.auth.model.entity.LoginUser;
import com.auth.model.entity.User;
import com.auth.system.service.MenuService;
import com.auth.system.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author MYH
 * @time 2023/04/04 下午 12:41
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserService userService;

    @Resource
    private MenuService menuService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户信息
        User user = userService.getByUsername(username);

        // 查询用户的权限信息
        List<String> permissions = menuService.findUserPermsList(user.getId());

        return new LoginUser(user, permissions);
    }

}
