package com.task.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 番茄钟记录视图对象，包含番茄钟信息及关联的任务信息
 */
@Data
public class PomodoroRecordVO {
    /**
     * 记录ID
     */
    private Long id;

    /**
     * 关联的任务ID
     */
    private Long taskId;

    /**
     * 任务标题
     */
    private String taskTitle;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 持续时长（分钟）
     */
    private Integer duration;

    /**
     * 状态（0-进行中，1-已完成，2-已中断）
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
    