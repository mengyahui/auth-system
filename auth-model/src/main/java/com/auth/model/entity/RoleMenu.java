package com.auth.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author MYH
 * @time 2023/04/03 下午 09:29
 */
@Data
public class RoleMenu implements Serializable {

    private Long id;

    private Long roleId;

    private Long menuId;

    private Date createTime;

    private Date updateTime;
}
