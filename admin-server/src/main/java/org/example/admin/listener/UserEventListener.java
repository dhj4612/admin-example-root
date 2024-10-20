package org.example.admin.listener;

import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import org.example.admin.model.event.SyncUserRoleAuthorityEvent;
import org.example.admin.service.SysUserService;
import org.example.framework.security.core.user.UserAuthorized;
import org.example.framework.utils.RedisUtil;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserEventListener {

    private final SysUserService sysUserService;

    @EventListener
    @Async("globalThreadPool")
    public void onSyncUserRoleAuthority(SyncUserRoleAuthorityEvent event) {
        Set<String> keys = RedisUtil.findKeysByPattern("login:session:*");
        keys.forEach(key -> {
            UserAuthorized userAuthorized = JSONUtil.toBean(RedisUtil.StringRedisTemplate.opsForValue().get(key), UserAuthorized.class);
            userAuthorized.setAuthorities(sysUserService.getUserAuthoritiesByUserId(userAuthorized.getId()));
            userAuthorized.setRoles(sysUserService.getUserRolesByUserId(userAuthorized.getId()));
            RedisUtil.StringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(userAuthorized));
        });
    }
}
