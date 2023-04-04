package com.auth.helper;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.enums.BooleanEnum;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author MYH
 * @time 2023/04/03 下午 09:10
 */
public class ExcelHelper {

    public static <V> void writeToWeb(HttpServletResponse response, List<V> data, Class<?> clazz, String name) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode(name, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        // 定义样式
        WriteCellStyle headStyle = ExcelHelper.headStyle();
        WriteCellStyle contentStyle = ExcelHelper.contentStyle();
        HorizontalCellStyleStrategy strategy = new HorizontalCellStyleStrategy(headStyle,contentStyle);

        // 写回数据
        EasyExcel
                .write(response.getOutputStream(), clazz)
                .autoCloseStream(Boolean.FALSE)
                .registerWriteHandler(strategy)
                .sheet("sheet1")
                .doWrite(data);

    }

    public static WriteCellStyle headStyle() {
        WriteCellStyle style = new WriteCellStyle();
        // 设置表头字体
        WriteFont headFont = new WriteFont();
        // 设置字体大小为20
        headFont.setFontHeightInPoints((short)20);
        // 设置字体斜体
        headFont.setItalic(BooleanEnum.TRUE.getBooleanValue());
        // 把字体对象设置到单元格样式对象中
        style.setWriteFont(headFont);

        style.setHorizontalAlignment(HorizontalAlignment.CENTER);
        style.setWrapped(false);
        return style;
    }

    // 设置表格内容样式
    public static WriteCellStyle contentStyle() {
        WriteCellStyle style = new WriteCellStyle();
        // 边框设置
        // style.setBorderTop(BorderStyle.THIN);			 // 设置单元格上边框为细线
        // style.setBorderBottom(BorderStyle.THICK);		 // 设置单元格下边框为粗线
        // style.setBorderLeft(BorderStyle.MEDIUM);	     // 设置单元格左边框为中线
        // style.setBorderRight(BorderStyle.MEDIUM_DASHED); // 设置单元格右边框为中虚线

        //内容居中
        style.setHorizontalAlignment(HorizontalAlignment.CENTER);
        return style;
    }
}
