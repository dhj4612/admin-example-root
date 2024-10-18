package org.example.framework.security.core.utils;

import cn.hutool.json.JSONUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.example.framework.common.exception.BizException;
import org.example.framework.security.config.JwtProperties;
import org.example.framework.security.core.user.UserAuthorized;
import org.example.framework.utils.AesUtil;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * JWT工具类
 */
@Slf4j
public class JwtUtil {
    private static JWTVerifier Verifier;
    private static JwtProperties JwtProperties;
    private static StringRedisTemplate StringRedisTemplate;

    public static void init(JwtProperties jwtProperties, StringRedisTemplate stringRedisTemplate) {
        Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecret().getBytes());
        Verifier = JWT.require(algorithm)
                .withIssuer("com.example.dhj")
                .build();
        JwtProperties = jwtProperties;
        StringRedisTemplate = stringRedisTemplate;

        log.info("JwtUtil init complete......");
    }

    public static String createJwtStr(UserAuthorized userAuthorized) {
        return JWT.create()
                .withJWTId(AesUtil.encrypt(String.valueOf(userAuthorized.getId())))
                .withIssuer("com.example.dhj")
                .withExpiresAt(DateUtils.addMilliseconds(new Date(), JwtProperties.getExpire()))
                .sign(Algorithm.HMAC256(JwtProperties.getSecret().getBytes()));
    }

    /**
     * 创建 jwt 并且缓存
     */
    public static String createJwtStrAndCache(UserAuthorized userAuthorized) {
        final String encryptUserId = AesUtil.encrypt(String.valueOf(userAuthorized.getId()));
        String jwt = JWT.create()
                .withJWTId(encryptUserId)
                .withIssuer("com.example.dhj")
                .withExpiresAt(DateUtils.addMilliseconds(new Date(), JwtProperties.getExpire()))
                .sign(Algorithm.HMAC256(JwtProperties.getSecret().getBytes()));
        StringRedisTemplate.opsForValue().set(
                "login:session:" + encryptUserId,
                JSONUtil.toJsonStr(userAuthorized),
                JwtProperties.getExpire(),
                TimeUnit.MILLISECONDS
        );
        return jwt;
    }

    /**
     * 创建 jwt 刷新令牌，过期时间为7天
     */
    public static String createRefreshJwtStr(UserAuthorized userAuthorized) {
        return JWT.create()
                .withJWTId(AesUtil.encrypt(String.valueOf(userAuthorized.getId())))
                .withExpiresAt(DateUtils.addDays(new Date(), 7))
                .sign(Algorithm.HMAC256(JwtProperties.getSecret().getBytes()));
    }

    /**
     * 校验 jwt 是否合法
     */
    public static DecodedJWT verifyJwtStr(String jwtStr) {
        return Verifier.verify(jwtStr);
    }

    /**
     * 校验 jwt 合法性并返回用户信息
     */
    public static UserAuthorized verifyJwtStrAndGet(String jwtStr) {
        DecodedJWT jwt = verifyJwtStr(jwtStr);
        int id = Integer.parseInt(AesUtil.decrypt(jwt.getId()));
        return new UserAuthorized().setId(id);
    }

    public static UserAuthorized verifyJwtStrAndGetForCache(String jwtStr) {
        DecodedJWT jwt = verifyJwtStr(jwtStr);
        String value = StringRedisTemplate.opsForValue().get("login:session:" + jwt.getId());
        if (StringUtils.isBlank(value) || !JSONUtil.isTypeJSON(value)) {
            throw BizException.valueOfMsg("用户授权令牌非法或已过期");
        }
        return JSONUtil.toBean(value, UserAuthorized.class);
    }
}
