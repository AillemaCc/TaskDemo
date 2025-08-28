// src/main/java/com/hmw/service/TaskPriorityService.java
package com.task.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.task.entity.TaskPriority;

import java.util.List;

public interface TaskPriorityService extends IService<TaskPriority> {
    // 按等级降序查所有
    List<TaskPriority> listPrioritiesOrderByLevel();

    // 新增优先级
    TaskPriority createPriority(TaskPriority priority);

    // 修改优先级
    void updatePriority(TaskPriority priority);

    // 等级是否存在
    boolean existsByLevel(Integer level);
}