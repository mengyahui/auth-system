package com.auth.model.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author MYH
 * @time 2023/04/01 上午 10:27
 */
@Data
@ApiModel("用户分配角色DTO")
public class AssignUserDto {

    @ApiModelProperty("用户id")
    @NotBlank(message = "用户id不能为空")
    public Long userId;

    @ApiModelProperty("角色列表")
    public List<Long> roleIds;

}
