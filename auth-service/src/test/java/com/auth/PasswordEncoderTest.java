package com.auth;

import com.auth.system.AuthSystemApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * @author MYH
 * @time 2023/04/04 下午 12:48
 */
@SpringBootTest(classes = AuthSystemApplication.class)
public class PasswordEncoderTest {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Test
    public void test() {
        String encode = passwordEncoder.encode("111111");
        System.out.println(encode);
    }
}
