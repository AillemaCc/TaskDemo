package com.task.common;

import lombok.Data;

/**
 * 统一API响应结果封装
 */
@Data
public class Result<T> {
    private int code; // 状态码：200成功，其他为错误
    private String msg; // 响应信息
    private T data; // 响应数据

    // 无参成功返回（用于不需要返回数据的场景）
    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMsg("操作成功");
        result.setData(null); // 数据为null
        return result;
    }

    // 带数据的成功返回
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMsg("操作成功");
        result.setData(data);
        return result;
    }

    // 失败返回
    public static <T> Result<T> fail(String msg) {
        Result<T> result = new Result<>();
        result.setCode(500);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    // 自定义状态码返回
    public static <T> Result<T> build(int code, String msg, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
}
    