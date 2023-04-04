package com.auth.model.dto.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author MYH
 * @time 2023/04/01 下午 04:12
 */
@Data
@ApiModel("角色条件搜索DTO")
public class SearchRoleDto {

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("开始时间")
    private Date startTime;

    @ApiModelProperty("结束时间")
    private Date endTime;

    @ApiModelProperty("页码")
    public Integer pageNum;

    @ApiModelProperty("每页个数")
    public Integer pageSize;
}
