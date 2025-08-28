// src/main/java/com/hmw/common/BusinessException.java
package com.task.common;

import lombok.Getter;

/**
 * 自定义业务异常：用于业务逻辑校验失败（如“任务不存在”“参数错误”）
 */
@Getter
public class BusinessException extends RuntimeException {
    // 错误状态码（400=参数错，404=资源不存在，403=禁止操作）
    private final Integer code;

    // 构造方法：状态码 + 错误消息
    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    // 快捷方法：避免重复写状态码（简化调用）
    // 1. 参数错误（400）
    public static BusinessException badRequest(String message) {
        return new BusinessException(400, message);
    }

    // 2. 资源不存在（404）
    public static BusinessException notFound(String message) {
        return new BusinessException(404, message);
    }

    // 3. 禁止操作（403）
    public static BusinessException forbidden(String message) {
        return new BusinessException(403, message);
    }
}