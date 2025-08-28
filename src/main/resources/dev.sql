-- 创建数据库
CREATE DATABASE IF NOT EXISTS task_management;
USE task_management;

-- 任务分类表
CREATE TABLE task_category (
                               id INT AUTO_INCREMENT PRIMARY KEY COMMENT '分类ID',
                               name VARCHAR(50) NOT NULL COMMENT '分类名称（工作、学习、生活等）',
                               description VARCHAR(200) DEFAULT NULL COMMENT '分类描述',
                               create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               is_deleted TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除标识（0-未删除，1-已删除）',
                               UNIQUE KEY uk_name (name, is_deleted) COMMENT '分类名称唯一（未删除状态下）'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务分类表';

-- 任务优先级表
CREATE TABLE task_priority (
                               id INT AUTO_INCREMENT PRIMARY KEY COMMENT '优先级ID',
                               name VARCHAR(20) NOT NULL COMMENT '优先级名称（低、中、高、紧急等）',
                               level INT NOT NULL COMMENT '优先级等级（数字越大优先级越高）',
                               create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               is_deleted TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除标识（0-未删除，1-已删除）',
                               UNIQUE KEY uk_level (level, is_deleted) COMMENT '优先级等级唯一（未删除状态下）'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务优先级表';

-- 任务表
CREATE TABLE task (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '任务ID',
                      title VARCHAR(100) NOT NULL COMMENT '任务标题',
                      content TEXT COMMENT '任务内容',
                      category_id INT NOT NULL COMMENT '分类ID，关联task_category表',
                      priority_id INT NOT NULL COMMENT '优先级ID，关联task_priority表',
                      deadline TIMESTAMP COMMENT '截止时间',
                      status TINYINT NOT NULL DEFAULT 0 COMMENT '任务状态（0-未开始，1-进行中，2-已完成，3-已取消）',
                      completion_time TIMESTAMP COMMENT '完成时间',
                      create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                      update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                      is_deleted TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除标识（0-未删除，1-已删除）'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务表';

-- 番茄钟记录表
CREATE TABLE pomodoro_record (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '记录ID',
                                 task_id BIGINT NOT NULL COMMENT '关联的任务ID，关联task表',
                                 start_time TIMESTAMP NOT NULL COMMENT '开始时间',
                                 end_time TIMESTAMP COMMENT '结束时间',
                                 duration INT COMMENT '持续时长（分钟）',
                                 status TINYINT DEFAULT 0 COMMENT '状态（0-进行中，1-已完成，2-已中断）',
                                 create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                 is_deleted TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除标识（0-未删除，1-已删除）'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='番茄钟记录表';

-- 任务完成情况统计表
CREATE TABLE task_statistics (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '统计ID',
                                 stat_date DATE NOT NULL COMMENT '统计日期',
                                 total_tasks INT DEFAULT 0 COMMENT '总任务数',
                                 completed_tasks INT DEFAULT 0 COMMENT '已完成任务数',
                                 pomodoro_count INT DEFAULT 0 COMMENT '番茄钟总数',
                                 pomodoro_duration INT DEFAULT 0 COMMENT '番茄钟总时长（分钟）',
                                 create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                 is_deleted TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除标识（0-未删除，1-已删除）',
                                 UNIQUE KEY uk_stat_date (stat_date, is_deleted) COMMENT '每天统计唯一（未删除状态下）'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务完成情况统计表';

-- 初始化分类数据
INSERT INTO task_category (name, description) VALUES
                                                  ('工作', '工作相关任务'),
                                                  ('学习', '学习提升相关任务'),
                                                  ('生活', '日常生活相关任务');

-- 初始化优先级数据
INSERT INTO task_priority (name, level) VALUES
                                            ('低', 1),
                                            ('中', 2),
                                            ('高', 3),
                                            ('紧急', 4);