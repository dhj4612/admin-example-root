package org.example.admin.model.result;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class SysUserInfoResult extends SysUserResult{
    private Set<Integer> roleIds;
}
