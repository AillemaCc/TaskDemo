// src/main/java/com/hmw/common/PageResult.java
package com.task.common;

import lombok.Data;
import java.util.List;

/**
 * 通用分页结果封装
 */
@Data
public class PageResult<T> {
    // 总记录数
    private Long total;
    // 总页数
    private Integer totalPages;
    // 当前页数据
    private List<T> list;
    // 当前页码
    private Integer pageNum;
    // 每页条数
    private Integer pageSize;

    public PageResult(Long total, List<T> list, Integer pageNum, Integer pageSize) {
        this.total = total;
        this.list = list;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        // 计算总页数
        this.totalPages = (int) Math.ceil(total * 1.0 / pageSize);
    }
}