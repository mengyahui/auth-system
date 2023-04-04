package com.auth.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author MYH
 * @time 2023/04/03 下午 09:17
 */

@Data
@ApiModel("菜单实体")
public class Menu implements Serializable {

    @ApiModelProperty("菜单id")
    private Long id;

    @ApiModelProperty("菜单父id")
    @NotBlank(message = "菜单父id不能为空")
    private Long parentId;

    @ApiModelProperty("菜单名称")
    @NotBlank(message = "菜单名称不能为空")
    private String name;

    @ApiModelProperty("菜单父类型(0：目录，1：菜单，2：按钮)")
    @NotBlank(message = "菜单类型不能为空")
    private Integer type;

    @ApiModelProperty("路由地址")
    private String path;

    @ApiModelProperty("组件路径")
    private String component;

    @ApiModelProperty("权限标识")
    private String perms;

    @ApiModelProperty("菜单图标")
    private String icon;

    @ApiModelProperty("排序字段")
    private Integer sortValue;

    @ApiModelProperty("菜单状态")
    private Integer status;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("字菜单列表")
    private List<Menu> children;

    private boolean selected;

    private static final long serialVersionUID = 1L;
}

