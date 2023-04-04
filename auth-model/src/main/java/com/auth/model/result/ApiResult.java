package com.auth.model.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author MYH
 * @time 2023/04/04 下午 03:46
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class ApiResult<T> {

    private Integer code;
    private String message;
    private T data;

    protected static <T> ApiResult<T> of(Integer code, String message, T data) {
        return new ApiResult<>(code, message, data);
    }

    public static <T> ApiResult<T> success() {
        return of(StatusEnum.SUCCESS.getCode(), StatusEnum.SUCCESS.getMessage(), null);
    }

    public static <T> ApiResult<T> success(String message) {
        return of(StatusEnum.SUCCESS.getCode(), message, null);
    }

    public static <T> ApiResult<T> success(T data) {
        return of(StatusEnum.SUCCESS.getCode(), StatusEnum.SUCCESS.getMessage(), data);
    }

    public static  <T> ApiResult<T> fail() {
        return of(StatusEnum.FAILED.getCode(), StatusEnum.FAILED.getMessage(), null);
    }
    public static  <T> ApiResult<T> fail(String message) {
        return of(StatusEnum.FAILED.getCode(), message, null);
    }

    public static <T> ApiResult<T> ofStatus(StatusEnum statusEnum) {
        return of(statusEnum.getCode(), statusEnum.getMessage(), null);
    }

    public static <T> ApiResult<T> ofException(Exception e) {
        return of( StatusEnum.FAILED.getCode(),e.getMessage(),null);
    }

}
