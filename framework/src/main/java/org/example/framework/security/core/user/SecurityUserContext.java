package org.example.framework.security.core.user;

import org.example.framework.common.exception.BizException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public final class SecurityUserContext {
    public static Integer getUserId() {
        return Optional
                .ofNullable(getUser())
                .map(UserAuthorized::getId)
                .orElse(null);
    }

    public static Integer ensureGetUserId() {
        UserAuthorized securityUser = ensureGetUser();
        return securityUser.getId();
    }

    public static UserAuthorized getUser() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserAuthorized user) {
            return user;
        }
        return null;
    }

    public static UserAuthorized ensureGetUser() {
        Object principal = Optional.ofNullable(
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal()
        ).orElseThrow(() -> BizException.valueOfMsg("当前登录用户不存在"));

        if (principal instanceof UserAuthorized user) {
            return user;
        }
        throw BizException.valueOfMsg("当前登录用户信息非法");
    }
}
