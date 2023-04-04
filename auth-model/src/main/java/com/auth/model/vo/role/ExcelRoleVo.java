package com.auth.model.vo.role;


import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.util.Date;

/**
 * @author MYH
 * @time 2023/04/01 下午 09:05
 */
@Data
public class ExcelRoleVo {

    @ExcelProperty("角色名称")
    @ColumnWidth(30)
    private String roleName;

    @ExcelProperty("角色编码")
    @ColumnWidth(30)
    private String roleCode;

    @ExcelProperty("角色描述")
    @ColumnWidth(30)
    private String description;

    @ExcelProperty(value = "角色创建时间")
    @ColumnWidth(30)
    @DateTimeFormat("yyyy年MM月dd日HH时mm分ss秒")
    private Date createTime;
}
