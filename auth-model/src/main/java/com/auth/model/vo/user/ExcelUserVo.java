package com.auth.model.vo.user;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.util.Date;

/**
 * @author MYH
 * @time 2023/03/31 下午 02:53
 */
@Data
public class ExcelUserVo {

    @ExcelProperty("账号")
    @ColumnWidth(30)
    private String username;

    @ExcelProperty("姓名")
    @ColumnWidth(30)
    private String name;

    @ExcelProperty("手机")
    @ColumnWidth(30)
    private String phone;

    @ExcelProperty("头像")
    @ColumnWidth(100)
    private String avatarUrl;

    @ExcelProperty(value = "创建时间")
    @ColumnWidth(30)
    @DateTimeFormat("yyyy年MM月dd日HH时mm分ss秒")
    private Date createTime;
}
