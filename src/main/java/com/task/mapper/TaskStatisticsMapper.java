package com.task.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.task.entity.TaskStatistics;
import com.task.vo.StatisticsVO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

/**
 * 任务统计Mapper接口
 */
@Mapper
public interface TaskStatisticsMapper extends BaseMapper<TaskStatistics> {

    /**
     * 查询指定日期范围内的统计数据
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 统计数据列表
     */
    List<StatisticsVO> selectByDateRange(LocalDate startDate, LocalDate endDate);

    /**
     * 查询指定日期的统计数据
     * @param statDate 统计日期
     * @return 统计数据
     */
    TaskStatistics selectByStatDate(LocalDate statDate);
}
    