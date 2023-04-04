package com.auth.model.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author MYH
 * @time 2023/04/04 下午 03:47
 */
@Getter
@AllArgsConstructor
public enum StatusEnum {
    SUCCESS(200,"操作成功"),
    FAILED(201, "操作失败"),
    SERVICE_ERROR(2012, "服务异常"),
    DATA_ERROR(204, "数据异常"),
    ILLEGAL_REQUEST(205, "非法请求"),
    REPEAT_SUBMIT(206, "重复提交"),
    ARGUMENT_VALID_ERROR(210, "参数校验异常"),

    LOGIN_AUTH(208, "未登陆"),
    NONE_PERMISSION(209, "没有权限"),
    ACCOUNT_ERROR(214, "账号不正确"),
    PASSWORD_ERROR(215, "密码不正确"),
    ACCOUNT_PASSWORD_ERROR(216, "账号或密码不正确"),
    ACCOUNT_STOP( 217, "账号已停用"),
    NODE_ERROR( 218, "该节点下有子节点，不可以删除"),
    TOKEN_ERROR( 219, "令牌校验失败");


    private final int code;
    private final String message;
}
