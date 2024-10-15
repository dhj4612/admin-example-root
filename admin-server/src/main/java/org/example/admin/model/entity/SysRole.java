package org.example.admin.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.framework.common.base.BaseEntity;

/**
 * 角色
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role")
public class SysRole extends BaseEntity {
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
     * 数据范围
     */
    private Integer dataScope;

    /**
     * 机构ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer orgId;

    /**
     * 租户ID
     */
    private Integer tenantId;
}
