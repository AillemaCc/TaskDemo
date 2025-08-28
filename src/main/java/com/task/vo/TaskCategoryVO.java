package com.task.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 任务分类视图对象，包含分类信息及关联的任务统计
 */
@Data
public class TaskCategoryVO {
    /**
     * 分类ID
     */
    private Integer id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 分类描述
     */
    private String description;

    /**
     * 该分类下的任务总数
     */
    private Integer taskCount;

    /**
     * 该分类下已完成的任务数
     */
    private Integer completedTaskCount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
    