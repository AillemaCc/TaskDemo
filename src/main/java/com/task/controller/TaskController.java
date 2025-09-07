// src/main/java/com/hmw/controller/TaskController.java
package com.task.controller;

import com.task.common.PageResult;
import com.task.common.Result;
import com.task.common.BusinessException;
import com.task.entity.Task;
import com.task.service.TaskService;
import com.task.vo.TaskQueryVO;
import com.task.vo.TaskVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Resource
    private TaskService taskService;

    // 创建任务
    @PostMapping
    public Result<Task> createTask(@RequestBody Task task) {
        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            throw BusinessException.badRequest("任务标题不能为空");
        }
        if (task.getCategoryId() == null) {
            throw BusinessException.badRequest("请选择任务分类");
        }
        if (task.getPriorityId() == null) {
            throw BusinessException.badRequest("请设置任务优先级");
        }
        return Result.success(taskService.createTask(task));
    }

    // 分页查询任务
    @GetMapping("/page")
    public Result<PageResult<TaskVO>> queryTaskPage(@RequestBody TaskQueryVO queryVO) {
        return Result.success(taskService.queryTaskPage(queryVO));
    }

    // 任务详情
    @GetMapping("/{id}")
    public Result<TaskVO> getTaskDetail(@PathVariable Long id) {
        TaskVO vo = taskService.getTaskDetail(id);
        if (vo == null) {
            throw BusinessException.notFound("任务不存在或已删除");
        }
        return Result.success(vo);
    }

    // 更新任务
    @PutMapping("/{id}")
    public Result<Void> updateTask(@PathVariable Long id, @RequestBody Task task) {
        task.setId(id);
        taskService.updateTask(task);
        return Result.success();
    }

    // 完成任务
    @PutMapping("/{id}/complete")
    public Result<Void> completeTask(@PathVariable Long id) {
        taskService.completeTask(id);
        return Result.success();
    }

    // 删除任务（逻辑删除）
    @DeleteMapping("/{id}")
    public Result<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return Result.success();
    }

    // 即将到期任务（3天内）
    @GetMapping("/expiring")
    public Result<List<TaskVO>> getExpiringTasks() {
        LocalDateTime threeDaysLater = LocalDateTime.now().plusDays(3);
        return Result.success(taskService.getTasksByStatusAndDeadline(0, threeDaysLater));
    }
}