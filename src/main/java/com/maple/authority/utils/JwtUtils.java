package com.maple.authority.utils;

import com.maple.authority.exception.AuthorityException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.maple.authority.configuration.BeanInitConfig.jwtProperties;


@Component
public class JwtUtils {


    /**
     * 生成token
     *
     * @param appId
     * @param appKey
     * @return
     * @throws Exception
     */
    public static String generateToken(String appId, String appKey) {
        Date date = new Date();

        Map<String, Object> claims = new HashMap<>();
        //放入租户信息
        String tenent = appId + "::" + appKey;
        claims.put("tenent", AesSecretUtils.encryptToStr(tenent, jwtProperties.getAesSecret()));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + jwtProperties.getExpire()))
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecret())
                .compact();
    }

    /**
     * 获取token参数
     *
     * @param token
     * @return
     */
    public static Claims getClaim(String token) throws AuthorityException {
        Claims claims = null;

        try {
            claims = Jwts.parser()
                    .setSigningKey(jwtProperties.getSecret())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new AuthorityException(401, "token非法或者已过期！");
        }

        return claims;
    }

    /**
     * 验证token是否失效
     *
     * @param token
     * @return true:过期   false:没过期
     */
    public static boolean isExpired(String token) {
        try {
            final Date expiration = getExpiration(token);
            return expiration.before(new Date());
        } catch (ExpiredJwtException | AuthorityException expiredJwtException) {
            return true;
        }
    }

    /**
     * 获取jwt失效时间
     */
    public static Date getExpiration(String token) throws AuthorityException {
        return getClaim(token).getExpiration();
    }

    public static void main(String[] args) throws AuthorityException {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJ0ZW5lbnQiOiJEOTIwOTJFMThCQjJBNzk3MEQzODM2RTNGMEZBQjVGODc3MzU3OUVGRUYyOEYzNkM5Q0Q3MDNGODFDQkFCQTc4RkUzNEJGODZDMjRCRjBEQkE2RDlFODA4Q0ZDM0FDM0MiLCJleHAiOjE1NzYyMDg2MzEsImlhdCI6MTU3NjIwODMzMX0.260DCK4c9-_Az3g8n2HdpeMEE7YO8xbHwiAAAohAMMu4Oy51V1WVQMaPtGJqZpABYi4zKcUmR_pPzOSDP9mHIQ";
        System.out.println(token);

        System.out.println(isExpired(token));

        Claims claims = getClaim(token);
        System.out.println(claims.get("tenent"));

    }
}
