package com.task.vo;

import lombok.Data;
import java.time.LocalDate;

/**
 * 任务统计数据视图对象，用于展示统计信息
 */
@Data
public class StatisticsVO {
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
     * 任务完成率（百分比）
     */
    private Double completionRate;

    /**
     * 番茄钟总数
     */
    private Integer pomodoroCount;

    /**
     * 番茄钟总时长（分钟）
     */
    private Integer pomodoroDuration;

    /**
     * 平均每个任务的番茄钟时长
     */
    private Double avgPomodoroPerTask;
}
