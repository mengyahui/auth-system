package com.auth.model.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author MYH
 * @time 2023/03/31 下午 01:09
 */
@Data
@ApiModel("修改用户DTO")
public class UpdateUserDto {

    @ApiModelProperty("用户id")
    @NotBlank(message = "用户id不能为空")
    private Long id;

    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty("手机号")
    @Length(max = 11,min = 11,message = "手机号的长度为11位")
    private String phone;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("用户头像地址")
    private String avatarUrl;

    @ApiModelProperty("更新时间")
    private Date updateTime;
}
