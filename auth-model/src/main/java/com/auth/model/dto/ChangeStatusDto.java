package com.auth.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author MYH
 * @time 2023/03/31 下午 09:27
 */
@Data
@ApiModel("改变状态DTO")
public class ChangeStatusDto {
    @ApiModelProperty("id")
    @NotNull(message = "用户id不能为空")
    public Long id;

    @ApiModelProperty("状态")
    @NotNull(message = "用户状态不能为空")
    public Integer status;
}
