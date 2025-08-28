package com.task.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 任务视图对象，包含任务基本信息及关联的分类和优先级名称
 */
@Data
public class TaskVO {
    /**
     * 任务ID
     */
    private Long id;

    /**
     * 任务标题
     */
    private String title;

    /**
     * 任务内容
     */
    private String content;

    /**
     * 分类ID
     */
    private Integer categoryId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 优先级ID
     */
    private Integer priorityId;

    /**
     * 优先级名称
     */
    private String priorityName;

    /**
     * 优先级等级
     */
    private Integer priorityLevel;

    /**
     * 截止时间
     */
    private LocalDateTime deadline;

    /**
     * 任务状态（0-未开始，1-进行中，2-已完成，3-已取消）
     */
    private Integer status;

    /**
     * 完成时间
     */
    private LocalDateTime completionTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
    