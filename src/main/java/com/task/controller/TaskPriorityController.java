// src/main/java/com/hmw/controller/TaskPriorityController.java
package com.task.controller;

import com.task.common.Result;
import com.task.common.BusinessException;
import com.task.entity.TaskPriority;
import com.task.service.TaskPriorityService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/priority")
public class TaskPriorityController {

    @Resource
    private TaskPriorityService taskPriorityService;

    // 所有优先级（按等级降序）
    @GetMapping
    public Result<List<TaskPriority>> getAllPriorities() {
        return Result.success(taskPriorityService.listPrioritiesOrderByLevel());
    }

    // 新增优先级
    @PostMapping
    public Result<TaskPriority> createPriority(@RequestBody TaskPriority priority) {
        if (priority.getName() == null || priority.getName().trim().isEmpty()) {
            throw BusinessException.badRequest("优先级名称不能为空");
        }
        if (priority.getLevel() == null) {
            throw BusinessException.badRequest("请设置优先级等级");
        }
        if (taskPriorityService.existsByLevel(priority.getLevel())) {
            throw BusinessException.badRequest("该等级已存在");
        }
        return Result.success(taskPriorityService.createPriority(priority));
    }

    // 修改优先级
    @PutMapping("/{id}")
    public Result<Void> updatePriority(@PathVariable Integer id, @RequestBody TaskPriority priority) {
        priority.setId(id);
        taskPriorityService.updatePriority(priority);
        return Result.success();
    }
}