package com.task.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.task.common.BusinessException;
import com.task.entity.PomodoroRecord;
import com.task.entity.Task;
import com.task.mapper.PomodoroRecordMapper;
import com.task.mapper.TaskMapper;
import com.task.service.PomodoroService;
import com.task.vo.PomodoroRecordVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PomodoroServiceImpl extends ServiceImpl<PomodoroRecordMapper, PomodoroRecord> implements PomodoroService {

    @Resource
    private TaskMapper taskMapper;

    @Resource
    private PomodoroRecordMapper pomodoroRecordMapper; // 直接注入PomodoroRecordMapper

    @Override
    public PomodoroRecord startPomodoro(Long taskId) {
        // 使用LambdaQueryWrapper查询任务
        LambdaQueryWrapper<Task> taskQuery = new LambdaQueryWrapper<>();
        taskQuery.eq(Task::getId, taskId)
                .eq(Task::getIsDeleted, 0);
        Task task = taskMapper.selectOne(taskQuery);

        if (task == null) {
            throw BusinessException.notFound("任务不存在");
        }

        // 创建番茄钟记录
        PomodoroRecord record = new PomodoroRecord();
        record.setTaskId(taskId);
        record.setStartTime(LocalDateTime.now());
        record.setStatus(0); // 0-进行中
        record.setCreateTime(LocalDateTime.now());
        record.setUpdateTime(LocalDateTime.now());
        record.setIsDeleted(0);
        pomodoroRecordMapper.insert(record);
        return record;
    }

    @Override
    public PomodoroRecordVO endPomodoro(Long id) {
        // 使用LambdaQueryWrapper查询番茄钟记录
        LambdaQueryWrapper<PomodoroRecord> query = new LambdaQueryWrapper<>();
        query.eq(PomodoroRecord::getId, id)
                .eq(PomodoroRecord::getIsDeleted, 0);
        PomodoroRecord record = pomodoroRecordMapper.selectOne(query);

        if (record == null) {
            throw BusinessException.notFound("番茄钟记录不存在");
        }
        if (record.getStatus() != 0) {
            throw BusinessException.badRequest("番茄钟已结束或中断");
        }

        // 更新状态+结束时间+时长（分钟）
        LocalDateTime endTime = LocalDateTime.now();
        int duration = (int) Duration.between(record.getStartTime(), endTime).toMinutes();

        record.setEndTime(endTime);
        record.setDuration(duration);
        record.setStatus(1); // 1-已完成
        record.setUpdateTime(LocalDateTime.now());
        pomodoroRecordMapper.updateById(record);

        // 转换VO
        return convertToVO(record);
    }

    @Override
    public void interruptPomodoro(Long id) {
        // 使用LambdaQueryWrapper查询番茄钟记录
        LambdaQueryWrapper<PomodoroRecord> query = new LambdaQueryWrapper<>();
        query.eq(PomodoroRecord::getId, id)
                .eq(PomodoroRecord::getIsDeleted, 0);
        PomodoroRecord record = pomodoroRecordMapper.selectOne(query);

        if (record == null) {
            throw BusinessException.notFound("番茄钟记录不存在");
        }
        if (record.getStatus() != 0) {
            throw BusinessException.badRequest("番茄钟已结束或中断");
        }

        record.setStatus(2); // 2-已中断
        record.setEndTime(LocalDateTime.now());
        record.setUpdateTime(LocalDateTime.now());
        pomodoroRecordMapper.updateById(record);
    }

    @Override
    public List<PomodoroRecordVO> getRecordsByTaskId(Long taskId) {
        // 使用LambdaQueryWrapper查询
        LambdaQueryWrapper<PomodoroRecord> query = new LambdaQueryWrapper<>();
        query.eq(PomodoroRecord::getTaskId, taskId)
                .eq(PomodoroRecord::getIsDeleted, 0)
                .orderByDesc(PomodoroRecord::getStartTime);

        List<PomodoroRecord> records = pomodoroRecordMapper.selectList(query);
        return records.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PomodoroRecordVO> getTodayRecords() {
        LocalDateTime todayStart = LocalDate.now().atStartOfDay();
        // 使用LambdaQueryWrapper查询
        LambdaQueryWrapper<PomodoroRecord> query = new LambdaQueryWrapper<>();
        query.ge(PomodoroRecord::getStartTime, todayStart)
                .eq(PomodoroRecord::getIsDeleted, 0)
                .orderByDesc(PomodoroRecord::getStartTime);

        List<PomodoroRecord> records = pomodoroRecordMapper.selectList(query);
        return records.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    // Entity转VO
    private PomodoroRecordVO convertToVO(PomodoroRecord record) {
        PomodoroRecordVO vo = new PomodoroRecordVO();
        BeanUtils.copyProperties(record, vo);

        // 使用LambdaQueryWrapper查询任务标题
        LambdaQueryWrapper<Task> taskQuery = new LambdaQueryWrapper<>();
        taskQuery.eq(Task::getId, record.getTaskId());
        Task task = taskMapper.selectOne(taskQuery);

        if (task != null) {
            vo.setTaskTitle(task.getTitle());
        }
        return vo;
    }
}
