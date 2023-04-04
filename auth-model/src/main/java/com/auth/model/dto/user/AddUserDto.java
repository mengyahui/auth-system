package com.auth.model.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author MYH
 * @time 2023/03/31 下午 12:01
 */
@Data
@ApiModel("添加用户DTO")
public class AddUserDto {
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("头像地址")
    private String avatarUrl;
    @ApiModelProperty("开始时间")
    private Date createTime;
    @ApiModelProperty("结束时间")
    private Date updateTime;
}
