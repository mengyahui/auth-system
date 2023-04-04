package com.auth.model.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author MYH
 * @time 2023/03/30 下午 07:50
 */
@Data
@ApiModel("用户条件搜索DTO")
public class SearchUserDto {
    @ApiModelProperty("页码")
    public Integer pageNum;

    @ApiModelProperty("每页个数")
    public Integer pageSize;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("开始时间")
    private Date startTime;

    @ApiModelProperty("结束时间")
    private Date endTime;


}
