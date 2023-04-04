package com.auth.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author MYH
 * @time 2023/03/30 下午 09:31
 */
@Data
public class PageVo implements Serializable {

    private Integer pageNum;
    private Integer pageSize;
    private Long total;
    private List<?> list;
}
