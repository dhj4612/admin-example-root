package org.example.framework.common.base;

import lombok.Getter;
import org.example.framework.common.constant.ResultEnum;

@Getter
public final class Result<T> {
    private final String code;
    private final String msg;
    private final T data;

    private Result(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = null;
    }

    public static <T> Result<T> ok() {
        return new Result<>(ResultEnum.OK.getCode(), ResultEnum.OK.getMsg());
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>(ResultEnum.OK.getCode(), ResultEnum.OK.getMsg(), data);
    }

    public static Result<?> okMsg(String msg) {
        return new Result<>(ResultEnum.OK.getCode(), msg);
    }

    public static <T> Result<T> ok(String msg, T data) {
        return new Result<>(ResultEnum.OK.getCode(), msg, data);
    }

    public static Result<?> err() {
        return new Result<>(ResultEnum.ERR.getCode(), ResultEnum.ERR.getMsg());
    }

    public static <T> Result<T> err(T data) {
        return new Result<>(ResultEnum.ERR.getCode(), ResultEnum.ERR.getMsg(), data);
    }

    public static <T> Result<T> errMsg(String msg) {
        return new Result<>(ResultEnum.ERR.getCode(), msg);
    }

    public static <T> Result<T> err(String msg, T data) {
        return new Result<>(ResultEnum.ERR.getCode(), msg, data);
    }

    public static <T> Result<T> err(String code, String msg) {
        return new Result<>(code, msg);
    }

    public static <T> Result<T> err(String code, String msg, T data) {
        return new Result<>(code, msg, data);
    }
}
