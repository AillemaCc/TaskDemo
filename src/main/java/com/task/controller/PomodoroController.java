// src/main/java/com/hmw/controller/PomodoroController.java
package com.task.controller;

import com.task.common.Result;
import com.task.common.BusinessException;
import com.task.entity.PomodoroRecord;
import com.task.service.PomodoroService;
import com.task.vo.PomodoroRecordVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/pomodoro")
@CrossOrigin
public class PomodoroController {

    @Resource
    private PomodoroService pomodoroService;

    // 开始番茄钟
    @PostMapping("/start")
    public Result<PomodoroRecord> startPomodoro(@RequestBody PomodoroRecord record) {
        if (record.getTaskId() == null) {
            throw BusinessException.badRequest("请关联任务");
        }
        return Result.success(pomodoroService.startPomodoro(record.getTaskId()));
    }

    // 结束番茄钟
    @PutMapping("/{id}/end")
    public Result<PomodoroRecordVO> endPomodoro(@PathVariable Long id) {
        return Result.success(pomodoroService.endPomodoro(id));
    }

    // 中断番茄钟
    @PutMapping("/{id}/interrupt")
    public Result<Void> interruptPomodoro(@PathVariable Long id) {
        pomodoroService.interruptPomodoro(id);
        return Result.success();
    }

    // 任务的番茄钟记录
    @GetMapping("/task/{taskId}")
    public Result<List<PomodoroRecordVO>> getByTaskId(@PathVariable Long taskId) {
        return Result.success(pomodoroService.getRecordsByTaskId(taskId));
    }

    // 今日番茄钟记录
    @GetMapping("/today")
    public Result<List<PomodoroRecordVO>> getTodayRecords() {
        return Result.success(pomodoroService.getTodayRecords());
    }
}