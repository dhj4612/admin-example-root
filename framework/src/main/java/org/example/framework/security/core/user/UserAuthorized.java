package org.example.framework.security.core.user;

import cn.hutool.core.collection.CollUtil;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Accessors(chain = true)
public class UserAuthorized {
    private Integer id;

    // 存放用户权限标识和角色标识的集合
    private Set<String> authorities;
    private Set<String> roles;

    /**
     * 返回适配 security 的用户权限&角色标识，方便使用 @PreAuthorize 注解校验权限
     */
    public Collection<? extends GrantedAuthority> getAuthoritiesAdaptionSecurity() {
        final Set<SimpleGrantedAuthority> resultAuthorities = new HashSet<>();
        if (CollUtil.isNotEmpty(this.authorities)) {
            resultAuthorities.addAll(authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet()));
        }
        if (CollUtil.isNotEmpty(this.roles)) {
            resultAuthorities.addAll(
                    roles.stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                            .collect(Collectors.toSet())
            );
        }
        return resultAuthorities;
    }
}
