package com.task.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.task.entity.PomodoroRecord;
import com.task.entity.Task;
import com.task.entity.TaskStatistics;
import com.task.mapper.PomodoroRecordMapper;
import com.task.mapper.TaskMapper;
import com.task.mapper.TaskStatisticsMapper;
import com.task.service.StatisticsService;
import com.task.vo.StatisticsVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StatisticsServiceImpl extends ServiceImpl<TaskStatisticsMapper, TaskStatistics> implements StatisticsService {

    @Resource
    private TaskMapper taskMapper;
    @Resource
    private PomodoroRecordMapper pomodoroMapper;
    @Resource
    private TaskStatisticsMapper statisticsMapper;

    @Override
    public StatisticsVO getTodayStatistics() {
        LocalDate today = LocalDate.now();
        // 先查是否已有今日统计
        TaskStatistics dbStat = statisticsMapper.selectByStatDate(today);
        if (dbStat != null) {
            return convertToVO(dbStat);
        }
        // 无则计算并同步
        syncTodayStatistics();
        return convertToVO(statisticsMapper.selectByStatDate(today));
    }

    @Override
    public List<StatisticsVO> getByDateRange(LocalDate startDate, LocalDate endDate) {
        return statisticsMapper.selectByDateRange(startDate, endDate);
    }

    @Override
    public List<StatisticsVO> getThisWeekStatistics() {
        // 本周一
        LocalDate monday = LocalDate.now().minusDays(LocalDate.now().getDayOfWeek().getValue() - 1);
        // 今日
        LocalDate today = LocalDate.now();
        return statisticsMapper.selectByDateRange(monday, today);
    }

    @Override
    public void syncTodayStatistics() {
        LocalDate today = LocalDate.now();
        LocalDateTime todayStart = today.atStartOfDay();
        LocalDateTime todayEnd = today.atTime(23, 59, 59);

        // 1. 统计今日创建的总任务数（未删除）
        LambdaQueryWrapper<Task> taskQuery = new LambdaQueryWrapper<>();
        taskQuery.ge(Task::getCreateTime, todayStart)
                .le(Task::getCreateTime, todayEnd)
                .eq(Task::getIsDeleted, 0);
        long totalTasks = taskMapper.selectCount(taskQuery);

        // 2. 统计今日完成的任务数
        LambdaQueryWrapper<Task> completedTaskQuery = new LambdaQueryWrapper<>();
        completedTaskQuery.ge(Task::getCompletionTime, todayStart)
                .le(Task::getCompletionTime, todayEnd)
                .eq(Task::getStatus, 2)
                .eq(Task::getIsDeleted, 0);
        long completedTasks = taskMapper.selectCount(completedTaskQuery);

        // 3. 统计今日番茄钟数（已完成）
        LambdaQueryWrapper<PomodoroRecord> pomodoroQuery = new LambdaQueryWrapper<>();
        pomodoroQuery.ge(PomodoroRecord::getStartTime, todayStart)
                .le(PomodoroRecord::getStartTime, todayEnd)
                .eq(PomodoroRecord::getStatus, 1)
                .eq(PomodoroRecord::getIsDeleted, 0);
        long pomodoroCount = pomodoroMapper.selectCount(pomodoroQuery);

        // 4. 统计今日番茄钟总时长
        List<PomodoroRecord> pomodoroRecords = pomodoroMapper.selectList(pomodoroQuery);
        Integer pomodoroDuration = pomodoroRecords.stream()
                .mapToInt(PomodoroRecord::getDuration)
                .sum();

        // 保存/更新统计
        TaskStatistics stat = new TaskStatistics();
        stat.setStatDate(today);
        stat.setTotalTasks((int) totalTasks);
        stat.setCompletedTasks((int) completedTasks);
        stat.setPomodoroCount((int) pomodoroCount);
        stat.setPomodoroDuration(pomodoroDuration);
        stat.setCreateTime(LocalDateTime.now());
        stat.setUpdateTime(LocalDateTime.now());
        stat.setIsDeleted(0);

        // 存在则更新，不存在则新增
        TaskStatistics old = statisticsMapper.selectByStatDate(today);
        if (old != null) {
            stat.setId(old.getId());
            statisticsMapper.updateById(stat);
        } else {
            statisticsMapper.insert(stat);
        }
    }

    // Entity转VO（计算完成率）
    private StatisticsVO convertToVO(TaskStatistics stat) {
        StatisticsVO vo = new StatisticsVO();
        BeanUtils.copyProperties(stat, vo);
        // 计算完成率（避免除零）
        if (stat.getTotalTasks() > 0) {
            double rate = (double) stat.getCompletedTasks() / stat.getTotalTasks() * 100;
            vo.setCompletionRate(Math.round(rate * 100) / 100.0); // 保留2位小数
        } else {
            vo.setCompletionRate(0.0);
        }
        return vo;
    }
}
    