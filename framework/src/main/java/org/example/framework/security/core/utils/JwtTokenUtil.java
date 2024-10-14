package org.example.framework.security.core.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.example.framework.security.config.JwtProperties;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Map;

/**
 * JWT工具类
 */
@Slf4j
@RequiredArgsConstructor
public class JwtTokenUtil {
    public JWTVerifier verifier;
    private final JwtProperties jwtProperties;

    @PostConstruct
    public void verifierInit() {
        Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecret().getBytes());
        verifier = JWT.require(algorithm)
                .withIssuer("com.example")
                .build();
        log.info("jwt verifier init complete......");
    }

    /**
     * 创建访问令牌
     */
    public String createAccessToken(Map<String, String> userInfo) {
        return JWT.create()
                .withJWTId(userInfo.get("id"))
                .withIssuer("com.example.dhj")
                .withClaim("type", userInfo.get("type"))
                .withExpiresAt(DateUtils.addMilliseconds(new Date(), jwtProperties.getExpire()))
                .sign(Algorithm.HMAC256(jwtProperties.getSecret().getBytes()));
    }

    /**
     * 创建 token 刷新令牌，过期时间为7天
     */
    public String createRefreshToken(Long userId) {
        return JWT.create()
                .withJWTId(String.valueOf(userId))
                .withExpiresAt(DateUtils.addDays(new Date(), 7))
                .sign(Algorithm.HMAC256(jwtProperties.getSecret().getBytes()));
    }

    /**
     * 校验 token 是否合法
     */
    public DecodedJWT verifyAccessToken(String accessToken) {
        return verifier.verify(accessToken);
    }

    /**
     * 校验 token 合法性并返回用户信息
     */
    public UserDetails verifyAccessTokenAndGet(String accessToken) {
        DecodedJWT jwt = verifyAccessToken(accessToken);
        //SecurityUserDTO user = new SecurityUserDTO();
        //user.setId(Integer.valueOf(jwt.getId()));
        //user.setType(UserType.valueOf(jwt.getClaim("type").asString()));
        //return user;
        return null;
    }
}
