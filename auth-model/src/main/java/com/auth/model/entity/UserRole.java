package com.auth.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author MYH
 * @time 2023/04/03 下午 09:29
 */
@Data
public class UserRole implements Serializable {

    private Long userId;
    private Long roleId;
    private Date createTime;
    private Date updateTime;
    private static final long serialVersionUID = 1L;

}
