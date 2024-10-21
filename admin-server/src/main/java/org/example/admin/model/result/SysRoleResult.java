package org.example.admin.model.result;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SysRoleResult {
    private Integer id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    public LocalDateTime createTime;
}
