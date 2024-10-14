package org.example.framework.security.core.handler;

import cn.hutool.json.JSONUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.framework.common.base.Result;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/**
 * 匿名用户（未携带token，抛出异常）处理器
 */
public class SecurityAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setContentType("application/json; charset=utf-8");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Origin", request.getHeader(HttpHeaders.ORIGIN));
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().print(JSONUtil.toJsonStr(Result.err(String.valueOf(HttpStatus.UNAUTHORIZED.value()), "请先登录~")));
    }
}
