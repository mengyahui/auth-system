package com.auth.model.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @author MYH
 * @time 2023/03/31 下午 12:01
 */
@Data
@ApiModel("添加用户DTO")
public class AddUserDto {
    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty("手机号")
    @Length(max = 11,min = 11,message = "手机号的长度为11位")
    private String phone;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("密码")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])[A-Za-z0-9]{6,12}$",message = "密码由6-12位字母和数字组成")
    private String password;

    @ApiModelProperty("头像地址")
    private String avatarUrl;

    @ApiModelProperty("开始时间")
    private Date createTime;

    @ApiModelProperty("结束时间")
    private Date updateTime;
}
