package com.auth.model.vo.menu;

import com.auth.model.entity.Menu;
import lombok.Data;

import java.util.List;

/**
 * @author MYH
 * @time 2023/04/02 上午 10:25
 */
@Data
public class MenuVo {

    private Long id;

    private Long parentId;

    private String name;

    private Integer type;

    private String path;

    private String component;

    private String perms;

    private Integer status;

    private List<Menu> children;
}
