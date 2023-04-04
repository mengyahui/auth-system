package com.auth.model.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author MYH
 * @time 2023/04/01 上午 10:27
 */
@Data
@ApiModel("用户分配角色DTO")
public class AssignUserDto {

    @ApiModelProperty("用户id")
    public Long userId;

    @ApiModelProperty("角色列表")
    public List<Long> roleIds;

}
