package com.task.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 任务统计实体类
 */
@Data
@TableName("task_statistics")
public class TaskStatistics {

    /**
     * 统计ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 统计日期
     */
    private LocalDate statDate;

    /**
     * 总任务数
     */
    private Integer totalTasks;

    /**
     * 已完成任务数
     */
    private Integer completedTasks;

    /**
     * 番茄钟总数
     */
    private Integer pomodoroCount;

    /**
     * 番茄钟总时长（分钟）
     */
    private Integer pomodoroDuration;

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
    