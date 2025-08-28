// src/main/java/com/hmw/controller/StatisticsController.java
package com.task.controller;

import com.task.common.Result;
import com.task.service.StatisticsService;
import com.task.vo.StatisticsVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    @Resource
    private StatisticsService statisticsService;

    // 今日统计
    @GetMapping("/today")
    public Result<StatisticsVO> getTodayStatistics() {
        return Result.success(statisticsService.getTodayStatistics());
    }

    // 日期范围统计
    @GetMapping("/date-range")
    public Result<List<StatisticsVO>> getByDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return Result.success(statisticsService.getByDateRange(startDate, endDate));
    }

    // 本周统计
    @GetMapping("/this-week")
    public Result<List<StatisticsVO>> getThisWeekStatistics() {
        return Result.success(statisticsService.getThisWeekStatistics());
    }
}