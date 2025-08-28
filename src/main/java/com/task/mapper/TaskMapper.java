package com.task.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.task.entity.Task;
import com.task.vo.TaskVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 任务Mapper接口
 */
@Mapper
public interface TaskMapper extends BaseMapper<Task> {

    /**
     * 分页查询任务列表，包含分类和优先级信息
     * @param page 分页参数
     * @param task 查询条件
     * @return 分页任务列表
     */
    IPage<TaskVO> selectTaskPage(Page<Task> page, Task task);

    /**
     * 根据ID查询任务详情，包含分类和优先级信息
     * @param id 任务ID
     * @return 任务详情VO
     */
    TaskVO selectTaskById(Long id);
}
    