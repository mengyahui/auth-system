package com.auth.exception;

import com.auth.model.result.StatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author MYH
 * @time 2023/04/04 下午 03:38
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SystemException extends RuntimeException{
    private Integer code;
    private String message;


    public SystemException(StatusEnum statusEnum) {
        this.code = statusEnum.getCode();
        this.message = statusEnum.getMessage();
    }

    public SystemException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
