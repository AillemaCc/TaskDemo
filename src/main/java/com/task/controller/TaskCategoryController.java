// src/main/java/com/hmw/controller/TaskCategoryController.java
package com.task.controller;

import com.task.common.Result;
import com.task.common.BusinessException;
import com.task.entity.TaskCategory;
import com.task.service.TaskCategoryService;
import com.task.service.TaskService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/api/category")
public class TaskCategoryController {

    @Resource
    private TaskCategoryService taskCategoryService;
    @Resource
    private TaskService taskService;

    // 所有分类
    @GetMapping
    public Result<List<TaskCategory>> getAllCategories() {
        return Result.success(taskCategoryService.listUndeleteCategories());
    }

    // 新增分类
    @PostMapping
    public Result<TaskCategory> createCategory(@RequestBody TaskCategory category) {
        if (category.getName() == null || category.getName().trim().isEmpty()) {
            throw BusinessException.badRequest("分类名称不能为空");
        }
        if (taskCategoryService.existsByName(category.getName())) {
            throw BusinessException.badRequest("该分类已存在");
        }
        return Result.success(taskCategoryService.createCategory(category));
    }

    // 修改分类
    @PutMapping("/{id}")
    public Result<Void> updateCategory(@PathVariable Integer id, @RequestBody TaskCategory category) {
        category.setId(id);
        taskCategoryService.updateCategory(category);
        return Result.success();
    }

    // 删除分类
    @DeleteMapping("/{id}")
    public Result<Void> deleteCategory(@PathVariable Integer id) {
        if (taskCategoryService.hasTasks(id)) {
            throw BusinessException.forbidden("该分类下有关联任务，无法删除");
        }
        taskCategoryService.deleteCategory(id);
        return Result.success();
    }
}