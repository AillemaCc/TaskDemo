package com.task;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 任务管理系统启动类
 */
@SpringBootApplication
@MapperScan("com.task.mapper") // 扫描MyBatis的Mapper接口
public class TaskApplication {

    public static void main(String[] args) {
        // 启动Spring Boot应用
        SpringApplication.run(TaskApplication.class, args);
    }
}