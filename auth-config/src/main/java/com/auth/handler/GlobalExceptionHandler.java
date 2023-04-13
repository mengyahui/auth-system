package com.auth.handler;

import com.auth.exception.SystemException;
import com.auth.model.result.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * @author MYH
 * @time 2023/04/04 下午 03:35
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    public static String getExceptionInfo(Exception e) {
        StackTraceElement element = e.getStackTrace()[0];
        String className = element.getClassName();
        String methodName = element.getMethodName();
        return className +":"+ methodName;
    }

    @ExceptionHandler(SystemException.class)
    public ApiResult<?> systemException(SystemException e) {
        log.error("系统异常:{}",e.getMessage());
        return ApiResult.fail(e.getMessage());
    }

    // 空指针异常
    @ExceptionHandler(NullPointerException.class)
    public ApiResult<?> nullPointerExceptionHandler(NullPointerException e) {
        log.error("空指针异常:{}",getExceptionInfo(e));
        return ApiResult.fail("空指针异常");
    }

    // 类型转换异常
    @ExceptionHandler(ClassCastException.class)
    public ApiResult<?> classCastExceptionHandler(ClassCastException e) {
        log.error("类型转换异常:{}",getExceptionInfo(e));
        return ApiResult.fail("类型转换异常");
    }

    // IO异常
    @ExceptionHandler(IOException.class)
    public ApiResult<?> iOExceptionHandler(IOException e) {
        log.error("IO异常:{}",getExceptionInfo(e));
        return ApiResult.fail("IO异常");
    }

    // 索引越界异常
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public ApiResult<?> indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException e) {
        log.error("索引越界异常:{}",getExceptionInfo(e));
        return ApiResult.fail("索引越界异常");
    }

    // 参数类型不匹配异常
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ApiResult<?> argumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException e) {
        log.error("参数类型不匹配异常:{}",getExceptionInfo(e));
        return ApiResult.fail("参数类型不匹配异常");
    }

    // 缺少参数异常
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ApiResult<?> missParamExceptionHandler(MissingServletRequestParameterException e) {
        log.error("缺少参数异常:{}",getExceptionInfo(e));
        return ApiResult.fail("缺少参数异常");
    }

    // 请求方法不匹配异常
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ApiResult<?> methodNotSupportExceptionHandler(HttpRequestMethodNotSupportedException e) {
        log.error("请求方法不匹配异常:{}",getExceptionInfo(e));
        return ApiResult.fail("请求方法不匹配异常");
    }

    // 控制器方法中@RequestBody类型参数数据类型转换异常
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiResult<?> messageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
        log.error("参数转换异常:{}",getExceptionInfo(e));
        return ApiResult.fail("参数转换异常");
    }


    // 请求体参数校验异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResult<?> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        log.error("参数校验异常:{}",objectError.getDefaultMessage());
        return ApiResult.fail(objectError.getDefaultMessage());
    }

    // 数据绑定异常
    @ExceptionHandler(BindException.class)
    public ApiResult<?> bindException(BindException e) {
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        log.error("数据绑定异常:{}",objectError.getDefaultMessage());
        return ApiResult.fail(objectError.getDefaultMessage());
    }

    // 参数校验异常
    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResult<?> constraintViolationExceptionHandler(ConstraintViolationException e) {
        String message = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList()).get(0);
        log.error("参数校验异常:{}",message);
        return ApiResult.fail(message);
    }

//    @ExceptionHandler(Exception.class)
//    public ApiResult<?> exceptionAllHandler(Exception e) {
//        log.error("未知异常:{}",e.getMessage());
//        return ApiResult.fail("未知异常");
//    }
}
