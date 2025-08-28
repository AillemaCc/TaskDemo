package com.task.vo;

import lombok.Data;
import java.time.LocalDate;

/**
 * 任务统计视图对象，用于展示统计信息及趋势分析
 */
@Data
public class TaskStatisticsVO {
    /**
     * 统计ID
     */
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
     * 完成率
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
     * 按分类统计的任务数
     */
    private CategoryTaskCountVO[] categoryTaskCounts;
}

// 内部类：按分类统计的任务数
@Data
class CategoryTaskCountVO {
    private Integer categoryId;
    private String categoryName;
    private Integer taskCount;
    private Integer completedCount;
}
    