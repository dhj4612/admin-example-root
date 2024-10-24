package org.example.framework.database.core;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.example.framework.security.core.user.SecurityUserContext;

import java.time.LocalDateTime;

/**
 * @author dhj
 */
public class AutoFillMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());  // 起始版本 3.3.0(推荐使用)
        this.strictInsertFill(metaObject, "deleted", Integer.class, 0);
        this.strictInsertFill(metaObject, "version", Integer.class, 0);

        Integer userId = SecurityUserContext.getUserId();
        if (userId != null) {
            this.strictInsertFill(metaObject, "creator", Integer.class, userId);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐使用)
        Integer userId = SecurityUserContext.getUserId();
        if (userId != null) {
            this.strictUpdateFill(metaObject, "updater", Integer.class, userId);
        }
    }
}
