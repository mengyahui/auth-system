package com.auth.model.vo.role;

import lombok.Data;

import java.util.Date;

/**
 * @author MYH
 * @time 2023/04/01 下午 04:21
 */
@Data
public class RoleVo {

    private Long id;

    private String roleName;

    private String roleCode;

    private String description;

    private Date createTime;

}
