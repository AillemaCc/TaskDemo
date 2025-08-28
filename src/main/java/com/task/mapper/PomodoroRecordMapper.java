package com.task.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.task.entity.PomodoroRecord;
import com.task.vo.PomodoroRecordVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 番茄钟记录Mapper接口
 */
@Mapper
public interface PomodoroRecordMapper extends BaseMapper<PomodoroRecord> {

    /**
     * 分页查询任务的番茄钟记录
     * @param page 分页参数
     * @param taskId 任务ID
     * @return 分页番茄钟记录列表
     */
    IPage<PomodoroRecordVO> selectByTaskId(Page<PomodoroRecord> page, Long taskId);
}
    