package org.example.admin.model.result;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class SysRoleInfoResult extends SysRoleResult {
    private Set<Integer> menuIds;
}
