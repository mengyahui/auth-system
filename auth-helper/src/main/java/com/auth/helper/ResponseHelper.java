package com.auth.helper;

import com.auth.model.result.ApiResult;
import com.fasterxml.jackson.databind.json.JsonMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseHelper {

    public static void write(HttpServletResponse response) {
        response.reset();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        try {
            response.getWriter().println(new JsonMapper().writeValueAsString(ApiResult.fail()));
        } catch (IOException e) {
            throw new RuntimeException("操作失败");
        }
    }

    public static void writeException(HttpServletResponse response,Exception ex) {
        response.reset();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        try {
            response.getWriter().println(new JsonMapper().writeValueAsString(ApiResult.ofException(ex)));
        } catch (IOException e) {
            throw new RuntimeException("操作失败");
        }
    }
}