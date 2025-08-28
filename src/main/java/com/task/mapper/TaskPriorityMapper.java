package com.task.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.task.entity.TaskPriority;
import org.apache.ibatis.annotations.Mapper;

/**
 * 任务优先级Mapper接口
 */
@Mapper
public interface TaskPriorityMapper extends BaseMapper<TaskPriority> {

}
    