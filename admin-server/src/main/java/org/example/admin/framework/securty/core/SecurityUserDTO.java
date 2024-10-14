package org.example.admin.framework.securty.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Accessors(chain = true)
public class SecurityUserDTO implements UserDetails {

    /**
     * 用户id
     */
    private Integer id;

    /**
     * 账户、用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 拥有权限集合
     */
    private Set<String> authorities;

    //public static SecurityUserDTO of(User user) {
    //    return new SecurityUserDTO()
    //            .setId(user.getId())
    //            .setType(user.getUserType())
    //            .setNickName(user.getNickname())
    //            .setState(user.getState());
    //}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (Objects.isNull(this.authorities) || this.authorities.isEmpty()) {
            return Collections.emptyList();
        }
        return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        //return UserState.Normal.equals(state);
        return true;
    }
}
