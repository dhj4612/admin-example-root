package org.example.admin.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.framework.common.base.BaseEntity;
import org.example.framework.database.core.DbFieldCryptoHandler;

/**
 * 用户
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user")
public class SysUser extends BaseEntity {
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 姓名
     */
    private String realName;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 性别   0：男   1：女   2：未知
     */
    private Integer gender;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    @TableField( typeHandler = DbFieldCryptoHandler.class)
    private String mobile;

    /**
     * 机构ID
     */
    private Integer orgId;

    /**
     * 超级管理员 0-否 1-是
     */
    private Integer superAdmin;

    /**
     * 状态 0-禁用 1-正常
     */
    private Integer status;

    /**
     * 机构名称
     */
    @TableField(exist = false)
    private String orgName;

    /**
     * 租户ID
     */
    private Integer tenantId;
}
