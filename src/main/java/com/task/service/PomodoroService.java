// src/main/java/com/hmw/service/PomodoroService.java
package com.task.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.task.entity.PomodoroRecord;
import com.task.vo.PomodoroRecordVO;

import java.util.List;

public interface PomodoroService extends IService<PomodoroRecord> {
    // 开始番茄钟
    PomodoroRecord startPomodoro(Long taskId);

    // 结束番茄钟
    PomodoroRecordVO endPomodoro(Long id);

    // 中断番茄钟
    void interruptPomodoro(Long id);

    // 任务的番茄钟记录
    List<PomodoroRecordVO> getRecordsByTaskId(Long taskId);

    // 今日番茄钟记录
    List<PomodoroRecordVO> getTodayRecords();
}