package com.auth.model.vo.user;

import lombok.Data;

import java.util.Date;

/**
 * @author MYH
 * @time 2023/03/30 下午 08:10
 */
@Data
public class UserVo {

    private Long id;
    private String username;
    private String name;
    private String phone;
    private String avatarUrl;
    private Integer status;
    private Date createTime;
}
