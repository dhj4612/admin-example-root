package org.example.framework.security.core.utils;

import org.example.framework.security.core.user.SecurityUserContext;
import org.example.framework.security.core.user.UserAuthorized;

import java.util.Objects;

public class SecurityExpressions {

    public boolean hasAuthorities(String authorities) {
        UserAuthorized userAuthorized = SecurityUserContext.ensureGetUser();
        if (userAuthorized.getAuthorities().contains("*")) {
            return true;
        }
        return userAuthorized.getAuthorities().stream().anyMatch(auth -> Objects.equals(authorities, auth));
    }

    public boolean hasRole(String role) {
        UserAuthorized userAuthorized = SecurityUserContext.ensureGetUser();
        if (userAuthorized.getRoles().contains("admin")) {
            return true;
        }
        return userAuthorized.getRoles().stream().anyMatch(r -> Objects.equals(role, r));
    }

    public boolean hasAnyAuthorities(String... authorities) {
        UserAuthorized userAuthorized = SecurityUserContext.ensureGetUser();
        if (userAuthorized.getAuthorities().contains("*")) {
            return true;
        }
        for (String authority : authorities) {
            if (userAuthorized.getAuthorities().stream().anyMatch(auth -> Objects.equals(authority, auth))) {
                return true;
            }
        }
        return false;
    }

    public boolean hasAnyRole(String... roles) {
        UserAuthorized userAuthorized = SecurityUserContext.ensureGetUser();
        if (userAuthorized.getRoles().contains("admin")) {
            return true;
        }
        for (String role : roles) {
            if (userAuthorized.getRoles().stream().anyMatch(r -> Objects.equals(role, r))) {
                return true;
            }
        }
        return false;
    }

    public boolean hasAllAuthorities(String... authorities) {
        UserAuthorized userAuthorized = SecurityUserContext.ensureGetUser();
        if (userAuthorized.getAuthorities().contains("*")) return true;
        boolean has = false;
        for (String authority : authorities) {
            has = userAuthorized.getAuthorities().stream().anyMatch(auth -> Objects.equals(authority, auth));
        }
        return has;
    }

    public boolean hasAllRoles(String... roles) {
        UserAuthorized userAuthorized = SecurityUserContext.ensureGetUser();
        if (userAuthorized.getRoles().contains("admin")) return true;
        boolean has = false;
        for (String role : roles) {
            has = userAuthorized.getRoles().stream().anyMatch(r -> Objects.equals(role, r));
        }
        return has;
    }
}
