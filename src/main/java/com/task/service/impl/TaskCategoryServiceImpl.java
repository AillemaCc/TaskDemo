package com.task.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.task.entity.Task;
import com.task.entity.TaskCategory;
import com.task.mapper.TaskCategoryMapper;
import com.task.mapper.TaskMapper;
import com.task.service.TaskCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskCategoryServiceImpl extends ServiceImpl<TaskCategoryMapper, TaskCategory> implements TaskCategoryService {

    @Resource
    private TaskMapper taskMapper;

    @Resource
    private TaskCategoryMapper taskCategoryMapper;

    @Override
    public List<TaskCategory> listUndeleteCategories() {
        LambdaQueryWrapper<TaskCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TaskCategory::getIsDeleted, 0)
                .orderByAsc(TaskCategory::getCreateTime);
        return taskCategoryMapper.selectList(queryWrapper);
    }

    @Override
    public TaskCategory createCategory(TaskCategory category) {
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        category.setIsDeleted(0);
        taskCategoryMapper.insert(category);
        return category;
    }

    @Override
    public void updateCategory(TaskCategory category) {
        TaskCategory old = taskCategoryMapper.selectById(category.getId());
        if (old == null || old.getIsDeleted() == 1) {
            throw new RuntimeException("分类不存在");
        }
        category.setCreateTime(old.getCreateTime());
        category.setIsDeleted(old.getIsDeleted());
        category.setUpdateTime(LocalDateTime.now());
        taskCategoryMapper.updateById(category);
    }

    @Override
    public void deleteCategory(Integer id) {
        TaskCategory update = new TaskCategory();
        update.setId(id);
        update.setIsDeleted(1);
        update.setUpdateTime(LocalDateTime.now());
        taskCategoryMapper.updateById(update);
    }

    @Override
    public boolean existsByName(String name) {
        LambdaQueryWrapper<TaskCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TaskCategory::getName, name)
                .eq(TaskCategory::getIsDeleted, 0);
        return taskCategoryMapper.selectCount(queryWrapper) > 0;
    }

    @Override
    public boolean hasTasks(Integer categoryId) {
        LambdaQueryWrapper<Task> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Task::getCategoryId, categoryId)
                .eq(Task::getIsDeleted, 0);
        return taskMapper.selectCount(queryWrapper) > 0;
    }
}
    