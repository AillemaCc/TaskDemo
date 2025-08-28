package com.task.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.task.entity.TaskPriority;
import com.task.mapper.TaskPriorityMapper;
import com.task.service.TaskPriorityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskPriorityServiceImpl extends ServiceImpl<TaskPriorityMapper, TaskPriority> implements TaskPriorityService {

    // 注入TaskPriorityMapper，直接通过Mapper操作数据
    @Resource
    private TaskPriorityMapper taskPriorityMapper;

    @Override
    public List<TaskPriority> listPrioritiesOrderByLevel() {
        // 构建LambdaQueryWrapper查询条件：未删除 + 按优先级等级降序
        LambdaQueryWrapper<TaskPriority> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TaskPriority::getIsDeleted, 0)
                .orderByDesc(TaskPriority::getLevel);
        // 通过Mapper查询列表，替代lambdaQuery()
        return taskPriorityMapper.selectList(queryWrapper);
    }

    @Override
    public TaskPriority createPriority(TaskPriority priority) {
        priority.setCreateTime(LocalDateTime.now());
        priority.setUpdateTime(LocalDateTime.now());
        priority.setIsDeleted(0);
        // 通过Mapper插入数据，替代ServiceImpl的save()
        taskPriorityMapper.insert(priority);
        return priority;
    }

    @Override
    public void updatePriority(TaskPriority priority) {
        // 通过Mapper查询原数据，替代ServiceImpl的getById()
        TaskPriority old = taskPriorityMapper.selectById(priority.getId());
        if (old == null || old.getIsDeleted() == 1) {
            throw new RuntimeException("优先级不存在");
        }
        priority.setCreateTime(old.getCreateTime());
        priority.setIsDeleted(old.getIsDeleted());
        priority.setUpdateTime(LocalDateTime.now());
        // 通过Mapper更新数据，替代ServiceImpl的updateById()
        taskPriorityMapper.updateById(priority);
    }

    @Override
    public boolean existsByLevel(Integer level) {
        // 构建LambdaQueryWrapper查询条件：指定等级 + 未删除
        LambdaQueryWrapper<TaskPriority> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TaskPriority::getLevel, level)
                .eq(TaskPriority::getIsDeleted, 0);
        // 通过Mapper统计数量，替代lambdaQuery()的count()
        return taskPriorityMapper.selectCount(queryWrapper) > 0;
    }
}