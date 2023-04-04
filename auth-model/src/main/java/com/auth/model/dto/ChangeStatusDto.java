package com.auth.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author MYH
 * @time 2023/03/31 下午 09:27
 */
@Data
@ApiModel("改变状态DTO")
public class ChangeStatusDto {
    @ApiModelProperty("id")
    public Long id;

    @ApiModelProperty("状态")
    public Integer status;
}
