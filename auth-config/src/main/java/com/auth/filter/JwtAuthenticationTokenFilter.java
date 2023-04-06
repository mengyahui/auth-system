package com.auth.filter;

import com.alibaba.fastjson.JSON;
import com.auth.exception.SystemException;
import com.auth.helper.JwtHelper;
import com.auth.helper.RedisHelper;
import com.auth.model.entity.LoginUser;
import com.auth.model.result.StatusEnum;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author MYH
 * @time 2023/04/04 下午 01:00
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Resource
    private RedisHelper redisHelper ;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 1、从请求头中获取token，如果请求头中不存在token，直接放行即可！由Spring Security的过滤器进行校验！
        String token = request.getHeader("token");
        if(token == null || "".equals(token)) {
            filterChain.doFilter(request , response);
            return ;
        }

        // 2、对token进行解析，取出其中的userId
        String userId = null ;
        try {
            userId = JwtHelper.getUserId(token);
        }catch (Exception e) {
            throw new SystemException(StatusEnum.TOKEN_ERROR) ;
        }

        // 3、使用userId从redis中查询对应的LoginUser对象
        LoginUser loginUser = redisHelper.getCacheObject("login_user:" + userId);

        if(loginUser != null) {
            // 4、然后将查询到的LoginUser对象的相关信息封装到UsernamePasswordAuthenticationToken对象中，然后将该对象存储到Security的上下文对象中
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginUser, null , loginUser.getAuthorities()) ;
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        // 5、放行
        filterChain.doFilter(request , response);
    }

}
