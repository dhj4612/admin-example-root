package org.example.framework.web.core;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.example.framework.common.base.Result;
import org.example.framework.common.exception.BizException;
import org.springframework.core.MethodParameter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author dhj
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result<?> paramMissHandler(MethodArgumentTypeMismatchException ex) {
        MethodParameter parameter = ex.getParameter();
        String parameterName = parameter.getParameterName();
        log.error("参数类型不匹配：", ex);
        return Result.errMsg("参数类型不匹配:" + parameterName);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Result<?> paramMissHandler(ConstraintViolationException ex) {
        log.error("参数错误：", ex);
        String multipartErrorMessage = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining());
        return Result.errMsg("参数错误：" + multipartErrorMessage);
    }

    @ExceptionHandler(IllegalStateException.class)
    public Result<?> illegalStateExceptionHandler(IllegalStateException ex) {
        log.error("状态异常：", ex);
        return Result.errMsg(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Result<?> illegalArgumentExceptionHandler(IllegalArgumentException ex) {
        log.error("参数非法：", ex);
        return Result.errMsg(ex.getMessage());
    }

    /**
     * hibernate validate 校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> methodArgumentNotValidHandler(MethodArgumentNotValidException ex) {
        log.error("参数校验异常：", ex);
        BindingResult bindingResult = ex.getBindingResult();
        List<ObjectError> errors = bindingResult.getAllErrors();
        Set<String> errs = errors.stream().map(err ->
                err instanceof FieldError fieldError ?
                        fieldError.getDefaultMessage() :
                        err.getDefaultMessage()).collect(Collectors.toSet());
        return Result.errMsg(String.join(",", errs));
    }

    @ExceptionHandler(BizException.class)
    public Result<?> bizExceptionHandler(BizException e) {
        log.error("业务异常：", e);
        return Result.errMsg(e.getMsg());
    }

    @ExceptionHandler(RuntimeException.class)
    public Result<?> runtimeExceptionHandler(RuntimeException e) {
        log.error("系统运行异常：", e);
        return Result.errMsg(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result<?> defaultExceptionHandler(Exception e) {
        log.error("系统错误：", e);
        return Result.errMsg("网络繁忙，稍后再试~");
    }
}
