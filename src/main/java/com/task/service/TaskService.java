// src/main/java/com/hmw/service/TaskService.java
package com.task.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.task.common.PageResult;
import com.task.entity.Task;
import com.task.vo.TaskQueryVO;
import com.task.vo.TaskVO;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskService extends IService<Task> {
    // 创建任务
    Task createTask(Task task);

    // 分页查询
    PageResult<TaskVO> queryTaskPage(TaskQueryVO queryVO);

    // 任务详情
    TaskVO getTaskDetail(Long id);

    // 更新任务
    void updateTask(Task task);

    // 完成任务
    void completeTask(Long id);

    // 删除任务
    void deleteTask(Long id);

    // 状态+截止时间筛选
    List<TaskVO> getTasksByStatusAndDeadline(Integer status, LocalDateTime deadline);
}