package com.task.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 任务实体类
 */
@Data
@TableName("task")
public class Task {

    /**
     * 任务ID
     */
    @TableId(type = IdType.AUTO)
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
     * 分类ID，关联task_category表
     */
    private Integer categoryId;

    /**
     * 优先级ID，关联task_priority表
     */
    private Integer priorityId;

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
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 逻辑删除标识（0-未删除，1-已删除）
     */
    @TableLogic
    private Integer isDeleted;
}
    