package com.auth.model.dto.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author MYH
 * @time 2023/04/02 下午 09:11
 */
@Data
@ApiModel("角色分配权限DTO")
public class AssignRoleDto {

    @ApiModelProperty("角色id")
    public Long roleId;

    @ApiModelProperty("菜单列表")
    public List<Long> menuIds;
}
