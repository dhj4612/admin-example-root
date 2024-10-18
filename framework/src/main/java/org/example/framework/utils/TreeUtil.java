package org.example.framework.utils;

import org.example.framework.common.base.TreeNode;

import java.util.List;
import java.util.Objects;

public class TreeUtil {

    public static <T extends TreeNode<T>> List<T> build(List<T> src) {
        List<T> topMenu = src.stream().filter(item -> Objects.equals(item.getPid(), 0)).toList();
        topMenu.forEach(item -> item.setChildren(findChildren(item, src)));
        return topMenu;
    }

    public static <T extends TreeNode<T>> List<T> findChildren(TreeNode<T> curr, List<T> src) {
        final Integer id = curr.getId();
        List<T> children = src.stream().filter(item -> Objects.equals(item.getPid(), id)).toList();
        curr.setChildren(children);
        if (!children.isEmpty()) {
            children.forEach(item -> item.setChildren(findChildren(item, src)));
        }
        return children;
    }
}
