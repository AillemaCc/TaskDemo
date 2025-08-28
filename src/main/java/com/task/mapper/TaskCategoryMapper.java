package com.task.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.task.entity.TaskCategory;
import org.apache.ibatis.annotations.Mapper;

/**
 * 任务分类Mapper接口
 * 继承BaseMapper获得基本CRUD操作
 */
@Mapper
public interface TaskCategoryMapper extends BaseMapper<TaskCategory> {

}
    