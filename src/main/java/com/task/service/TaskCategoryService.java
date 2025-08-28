// src/main/java/com/hmw/service/TaskCategoryService.java
package com.task.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.task.entity.TaskCategory;

import java.util.List;

public interface TaskCategoryService extends IService<TaskCategory> {
    // 未删除的分类
    List<TaskCategory> listUndeleteCategories();

    // 新增分类
    TaskCategory createCategory(TaskCategory category);

    // 修改分类
    void updateCategory(TaskCategory category);

    // 删除分类
    void deleteCategory(Integer id);

    // 分类名称是否存在
    boolean existsByName(String name);

    // 分类是否有关联任务
    boolean hasTasks(Integer categoryId);
}