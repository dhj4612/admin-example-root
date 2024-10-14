package org.example.admin.framework.securty.core;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.framework.security.config.JwtProperties;
import org.example.framework.utils.HttpServletUtil;
import org.example.framework.security.core.utils.JwtTokenUtil;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthenticationTokenFilter extends OncePerRequestFilter {

    private final JwtProperties jwtProperties;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain) {
        try {
            String authorizationHeader = request.getHeader(jwtProperties.getHeaderKey());
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(null);
            SecurityContextHolder.setContext(context);

            // 没有携带 token，放行空的 context，交给 security 处理
            if (StringUtils.isBlank(authorizationHeader)
                    || !authorizationHeader.startsWith("Bearer ")) { // 遵循 OAuth2 认证协议请求头
                chain.doFilter(request, response);
                return;
            }

            String jwt = authorizationHeader.substring(7);
            DecodedJWT decodedJWT = jwtTokenUtil.verifyAccessToken(jwt);

            // TODO 设置用户上下文信息
            //UsernamePasswordAuthenticationToken authentication =
            //        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            //authentication.setDetails(
            //        new WebAuthenticationDetailsSource().buildDetails(request)
            //);
            //context.setAuthentication(authentication);

            // 放行
            chain.doFilter(request, response);
        } catch (JWTVerificationException e) {
            HttpServletUtil.responseExceptionJsonData(response, "非法授权");
        } catch (Throwable e) {
            HttpServletUtil.responseExceptionJsonData(response, "登录校验失败");
        }
    }
}
