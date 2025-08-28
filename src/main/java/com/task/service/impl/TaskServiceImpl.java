package com.task.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.task.common.PageResult;
import com.task.common.BusinessException;
import com.task.entity.Task;
import com.task.entity.TaskCategory;
import com.task.entity.TaskPriority;
import com.task.mapper.TaskMapper;
import com.task.service.TaskCategoryService;
import com.task.service.TaskPriorityService;
import com.task.service.TaskService;
import com.task.vo.TaskQueryVO;
import com.task.vo.TaskVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {

    @Resource
    private TaskCategoryService categoryService;
    @Resource
    private TaskPriorityService priorityService;
    // 注入TaskMapper，统一通过Mapper操作数据，替代ServiceImpl封装方法
    @Resource
    private TaskMapper taskMapper;

    @Override
    public Task createTask(Task task) {
        // 校验分类/优先级是否存在
        TaskCategory category = categoryService.getById(task.getCategoryId());
        if (category == null || category.getIsDeleted() == 1) {
            throw BusinessException.notFound("分类不存在");
        }
        TaskPriority priority = priorityService.getById(task.getPriorityId());
        if (priority == null || priority.getIsDeleted() == 1) {
            throw BusinessException.notFound("优先级不存在");
        }
        // 设置默认值
        task.setStatus(0); // 0-未开始
        task.setCreateTime(LocalDateTime.now());
        task.setUpdateTime(LocalDateTime.now());
        task.setIsDeleted(0);
        // 用Mapper的insert()替代ServiceImpl的save()
        taskMapper.insert(task);
        return task;
    }

    @Override
    public PageResult<TaskVO> queryTaskPage(TaskQueryVO queryVO) {
        // 分页参数默认值
        Integer pageNum = ObjectUtils.isEmpty(queryVO.getPageNum()) ? 1 : queryVO.getPageNum();
        Integer pageSize = ObjectUtils.isEmpty(queryVO.getPageSize()) ? 10 : queryVO.getPageSize();

        // 用LambdaQueryWrapper替代LambdaQueryChainWrapper，构建动态查询条件
        LambdaQueryWrapper<Task> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!ObjectUtils.isEmpty(queryVO.getCategoryId()), Task::getCategoryId, queryVO.getCategoryId())
                .eq(!ObjectUtils.isEmpty(queryVO.getPriorityId()), Task::getPriorityId, queryVO.getPriorityId())
                .eq(!ObjectUtils.isEmpty(queryVO.getStatus()), Task::getStatus, queryVO.getStatus())
                .like(!ObjectUtils.isEmpty(queryVO.getTitle()), Task::getTitle, queryVO.getTitle())
                .le(!ObjectUtils.isEmpty(queryVO.getDeadline()), Task::getDeadline, queryVO.getDeadline())
                .ge(!ObjectUtils.isEmpty(queryVO.getCreateTimeStart()), Task::getCreateTime, queryVO.getCreateTimeStart())
                .le(!ObjectUtils.isEmpty(queryVO.getCreateTimeEnd()), Task::getCreateTime, queryVO.getCreateTimeEnd())
                .eq(Task::getIsDeleted, 0)
                .orderByDesc(Task::getUpdateTime);

        // 用Mapper的selectPage()替代ServiceImpl的page()，实现分页查询
        IPage<Task> page = taskMapper.selectPage(new Page<>(pageNum, pageSize), queryWrapper);
        // 转换VO
        List<TaskVO> voList = page.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return new PageResult<>(page.getTotal(), voList, pageNum, pageSize);
    }

    @Override
    public TaskVO getTaskDetail(Long id) {
        // 用LambdaQueryWrapper+Mapper的selectOne()替代lambdaQuery().one()
        LambdaQueryWrapper<Task> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Task::getId, id)
                .eq(Task::getIsDeleted, 0);
        Task task = taskMapper.selectOne(queryWrapper);
        return task != null ? convertToVO(task) : null;
    }

    @Override
    public void updateTask(Task task) {
        // 校验任务是否存在：用Mapper的selectById()替代ServiceImpl的getById()
        Task oldTask = taskMapper.selectById(task.getId());
        if (oldTask == null || oldTask.getIsDeleted() == 1) {
            throw BusinessException.notFound("任务不存在");
        }
        // 仅更新允许修改的字段
        task.setCreateTime(oldTask.getCreateTime());
        task.setIsDeleted(oldTask.getIsDeleted());
        task.setUpdateTime(LocalDateTime.now());
        // 用Mapper的updateById()替代ServiceImpl的updateById()
        taskMapper.updateById(task);
    }

    @Override
    public void completeTask(Long id) {
        // 用Mapper的selectById()替代ServiceImpl的getById()，校验任务存在性
        Task task = taskMapper.selectById(id);
        if (task == null || task.getIsDeleted() == 1) {
            throw BusinessException.notFound("任务不存在");
        }
        if (task.getStatus() == 2) {
            throw BusinessException.badRequest("任务已完成");
        }
        // 更新状态+完成时间
        Task updateTask = new Task();
        updateTask.setId(id);
        updateTask.setStatus(2); // 2-已完成
        updateTask.setCompletionTime(LocalDateTime.now());
        updateTask.setUpdateTime(LocalDateTime.now());
        // 用Mapper的updateById()替代ServiceImpl的updateById()
        taskMapper.updateById(updateTask);
    }

    @Override
    public void deleteTask(Long id) {
        // 用Mapper的selectById()替代ServiceImpl的getById()，校验任务存在性
        Task task = taskMapper.selectById(id);
        if (task == null || task.getIsDeleted() == 1) {
            throw BusinessException.notFound("任务不存在");
        }
        // 逻辑删除
        Task updateTask = new Task();
        updateTask.setId(id);
        updateTask.setIsDeleted(1);
        updateTask.setUpdateTime(LocalDateTime.now());
        // 用Mapper的updateById()替代ServiceImpl的updateById()
        taskMapper.updateById(updateTask);
    }

    @Override
    public List<TaskVO> getTasksByStatusAndDeadline(Integer status, LocalDateTime deadline) {
        // 用LambdaQueryWrapper+Mapper的selectList()替代lambdaQuery().list()
        LambdaQueryWrapper<Task> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Task::getStatus, status)
                .le(Task::getDeadline, deadline)
                .eq(Task::getIsDeleted, 0)
                .orderByAsc(Task::getDeadline);
        List<Task> taskList = taskMapper.selectList(queryWrapper);

        return taskList.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    // Entity转VO（业务逻辑不变，保留原实现）
    private TaskVO convertToVO(Task task) {
        TaskVO vo = new TaskVO();
        BeanUtils.copyProperties(task, vo);
        // 补充分类名称
        TaskCategory category = categoryService.getById(task.getCategoryId());
        if (category != null) {
            vo.setCategoryName(category.getName());
        }
        // 补充优先级名称+等级
        TaskPriority priority = priorityService.getById(task.getPriorityId());
        if (priority != null) {
            vo.setPriorityName(priority.getName());
            vo.setPriorityLevel(priority.getLevel());
        }
        return vo;
    }
}