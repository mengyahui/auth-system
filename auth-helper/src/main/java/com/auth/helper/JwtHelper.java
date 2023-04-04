package com.auth.helper;

import io.jsonwebtoken.*;

import java.util.Date;
import java.util.Objects;

/**
 * @author MYH
 * @time 2023/04/04 上午 09:10
 */
public class JwtHelper {

    // 签名密钥
    private static final String signKey = "ILoveJava";
    // 过期时间一周
    private static final long expirationTime = 1000 * 60 * 60 * 24 * 7;


    public static String createToken(String userId, String username) {
        return Jwts.builder()
                .setSubject("auth-system")
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .claim("userId", userId)
                .claim("username", username)
                .signWith(SignatureAlgorithm.HS512, signKey)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
    }

    public static String getUserId(String token) {
        if (Objects.equals(token, "")) return null;
        Jws<Claims> claimJwt = Jwts.parser().setSigningKey(signKey).parseClaimsJws(token);
        Claims claims = claimJwt.getBody();
        return (String) claims.get("userId");
    }

    public static String getUsername(String token) {

        if (Objects.equals(token, "")) return "";
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(signKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return (String) claims.get("username");
    }

    public static void main(String[] args) {
        String token = createToken("1", "admin");
        System.out.println(token);
    }
}
