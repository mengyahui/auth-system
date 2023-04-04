package com.auth.model.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author MYH
 * @time 2023/03/31 下午 01:09
 */
@Data
@ApiModel("修改用户DTO")
public class UpdateUserDto {

    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("用户头像地址")
    private String avatarUrl;

    @ApiModelProperty("更新时间")
    private Date updateTime;
}
