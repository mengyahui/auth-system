package com.auth.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @author MYH
 * @time 2023/04/03 下午 09:27
 */
@Data
@ApiModel("角色实体")
public class Role implements Serializable {

    @ApiModelProperty("角色id")
    @NotBlank(message = "角色id不能为空")
    private Long id;

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

    private static final long serialVersionUID = 1L;
}
