package org.example.framework.common.base;

import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@ToString
public class BaseQuery implements Serializable {
    /**
     * 当前页
     */
    private Long current;
    /**
     * 每页数
     */
    private Long pageSize;

    /**
     * 是否分页
     */
    private Boolean isPage;

    public Long getCurrent() {
        return current == null ? 1 : current;
    }

    public Long getPageSize() {
        return pageSize == null ? 10 : pageSize;
    }

    public boolean isPage() {
        return isPage == null || isPage;
    }
}
