package org.example.admin.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.example.framework.common.base.BaseEntity;

import java.io.Serial;

/**
 * 用户角色关系
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user_role")
@Accessors(chain = true)
public class SysUserRole extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 用户ID
     */
    private Integer userId;

}
