package com.auth.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MYH
 * @time 2023/04/04 下午 12:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser implements UserDetails {

    private User user;

    public LoginUser(User user,List<String> permissions) {
        this.user = user;
        this.permissions = permissions;
    }

    private List<String> permissions;

    private List<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return permissions.stream()
        .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {          // 账号是否没有过期
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {           // 账号是否没有被锁定
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {      // 账号的凭证是否没有过期
        return true;
    }

    @Override
    public boolean isEnabled() {                    // 账号是否可用
        return true;
    }
}
