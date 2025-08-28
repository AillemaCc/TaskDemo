package com.task.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 任务优先级实体类
 */
@Data
@TableName("task_priority")
public class TaskPriority {

    /**
     * 优先级ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 优先级名称
     */
    private String name;

    /**
     * 优先级等级（数字越大优先级越高）
     */
    private Integer level;

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
    