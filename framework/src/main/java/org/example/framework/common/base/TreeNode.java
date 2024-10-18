package org.example.framework.common.base;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class TreeNode<T> {
    private Integer id;
    private Integer pid;
    private List<T> children;
}
