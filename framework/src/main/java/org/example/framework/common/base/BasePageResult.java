package org.example.framework.common.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.function.Supplier;

@Data
public final class BasePageResult<T> implements Serializable {
    /**
     * 当前页
     */
    private Long current;

    /**
     * 每页记录数
     */
    private Long pageSize;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 总页数
     */
    private Long pageTotal;

    /**
     * 数据行
     */
    List<T> records;

    public BasePageResult() {
    }

    public BasePageResult(IPage<T> page) {
        this.current = page.getCurrent();
        this.pageSize = page.getSize();
        this.pageTotal = page.getPages();
        this.total = page.getTotal();
        this.records = page.getRecords();
    }

    public static <S, T> BasePageResult<T> ofPage(IPage<S> page, Supplier<List<T>> converter) {
        BasePageResult<T> pageResult = new BasePageResult<>();
        pageResult.setCurrent(page.getCurrent());
        pageResult.setPageSize(page.getSize());
        pageResult.setPageTotal(page.getPages());
        pageResult.setTotal(page.getTotal());
        pageResult.setRecords(converter.get());
        return pageResult;
    }

    public BasePageResult(Long page, Long pageSize, Long total, Long pageTotal, List<T> records) {
        this.current = page;
        this.pageSize = pageSize;
        this.total = total;
        this.records = records;
        this.pageTotal = pageTotal;
    }

    public BasePageResult(Long page, Long pageSize, Long total, List<T> records) {
        this.current = page;
        this.pageSize = pageSize;
        this.total = total;
        this.records = records;
        if (pageSize > 0) {
            this.pageTotal = (long) Math.ceil((double) total / pageSize);
        }
    }
}
