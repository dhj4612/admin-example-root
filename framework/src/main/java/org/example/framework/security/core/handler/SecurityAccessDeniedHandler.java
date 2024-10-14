package org.example.framework.security.core.handler;

import cn.hutool.json.JSONUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.framework.common.base.Result;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

/**
 * 未授权的错误处理器
 */
public class SecurityAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json; charset=utf-8");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Origin", request.getHeader(HttpHeaders.ORIGIN));
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.getWriter().write(JSONUtil.toJsonStr(Result.err(String.valueOf(HttpStatus.FORBIDDEN.value()), "未授权的访问~")));
    }
}
