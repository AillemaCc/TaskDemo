package com.task.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 任务查询条件视图对象，用于接收前端传递的查询参数
 */
@Data
public class TaskQueryVO {
    /**
     * 任务标题（模糊查询）
     */
    private String title;

    /**
     * 分类ID
     */
    private Integer categoryId;

    /**
     * 优先级ID
     */
    private Integer priorityId;

    /**
     * 任务状态
     */
    private Integer status;

    /**
     * 截止时间（小于等于）
     */
    private LocalDateTime deadline;

    /**
     * 开始时间（创建时间大于等于）
     */
    private LocalDateTime createTimeStart;

    /**
     * 结束时间（创建时间小于等于）
     */
    private LocalDateTime createTimeEnd;

    /**
     * 分页页码
     */
    private Integer pageNum = 1;

    /**
     * 分页大小
     */
    private Integer pageSize = 10;
}
    