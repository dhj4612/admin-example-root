package org.example.admin.model.result;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SysUserResult {
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 姓名
     */
    private String realName;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 性别 0-男 1-女 2-未知
     */
    private Integer gender;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
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
     * 创建时间
     */
    public LocalDateTime createTime;
}
