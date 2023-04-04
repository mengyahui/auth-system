package com.auth.model.dto.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author MYH
 * @time 2023/04/01 下午 05:46
 */
@Data
@ApiModel("添加角色DTO")
public class AddRoleDto {

    @ApiModelProperty("角色名称")
    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    @ApiModelProperty("角色编码")
    @NotBlank(message = "角色编码不能为空")
    private String roleCode;

    @ApiModelProperty("角色描述")
    @NotBlank(message = "角色描述不能为空")
    private String description;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("修改时间")
    private Date updateTime;
}
