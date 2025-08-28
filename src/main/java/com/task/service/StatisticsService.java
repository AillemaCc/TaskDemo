// src/main/java/com/hmw/service/StatisticsService.java
package com.task.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.task.entity.TaskStatistics;
import com.task.vo.StatisticsVO;

import java.time.LocalDate;
import java.util.List;

public interface StatisticsService extends IService<TaskStatistics> {
    // 今日统计
    StatisticsVO getTodayStatistics();

    // 日期范围统计
    List<StatisticsVO> getByDateRange(LocalDate startDate, LocalDate endDate);

    // 本周统计
    List<StatisticsVO> getThisWeekStatistics();

    // 同步今日统计（定时任务/完成任务时调用）
    void syncTodayStatistics();
}