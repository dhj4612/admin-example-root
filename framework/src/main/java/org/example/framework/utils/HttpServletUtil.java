package org.example.framework.utils;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.example.framework.common.base.Result;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpServletUtil {

    public static HttpServletRequest getHttpServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        return ((ServletRequestAttributes) requestAttributes).getRequest();
    }

    public static HttpServletResponse getHttpServletResponse() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        return ((ServletRequestAttributes) requestAttributes).getResponse();
    }

    /**
     * 解析请求中的 json 参数
     *
     * @param keys 指定读取的参数
     * @return 返回参数名和参数值的map集合
     */
    public static Map<String, Object> parseRequestJsonParamsByNames(HttpServletRequest request, String... keys) {
        String jsonStr = requestJsonBodyReaderAsStr(request);
        JSONObject paramsNode = JSONUtil.parseObj(jsonStr);
        Map<String, Object> paramsMap = new HashMap<>(10);
        for (String key : keys) {
            String value = paramsNode.getStr(key);
            paramsMap.put(key, value);
        }
        return paramsMap;
    }

    /**
     * 向客户端响应异常信息的的json数据
     */
    @SneakyThrows
    public static void responseExceptionJsonData(HttpServletResponse response, Throwable e) {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setHeader("Content-Type", "application/json");
        response.getWriter()
                .write(JSONUtil.toJsonStr(Result.err(e.getMessage())));
    }

    /**
     * 向客户端响应指定异常信息的json数据
     */
    @SneakyThrows
    public static void responseExceptionJsonData(HttpServletResponse response, String message) {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setHeader("Content-Type", "application/json");
        response.getWriter()
                .write(JSONUtil.toJsonStr(Result.err(message)));
    }

    /**
     * 解析请求中的 json 参数（将会解析所有参数）
     *
     * @return 返回参数名和参数值的map集合
     */
    public static Map<String, Object> parseRequestJsonParams(HttpServletRequest request) {
        String lineJoin = requestJsonBodyReaderAsStr(request);
        JSONObject paramsNode = JSONUtil.parseObj(lineJoin);
        Map<String, Object> paramsMap = new HashMap<>(10);
        paramsNode.keySet().forEach(fieldName -> {
            String value = paramsNode.getStr(fieldName);
            paramsMap.put(fieldName, value);
        });
        return paramsMap;
    }

    /**
     * 从 request 中读取 JsonBody 字符串
     */
    public static String requestJsonBodyReaderAsStr(HttpServletRequest request) {
        try (BufferedReader buf = new BufferedReader(request.getReader())) {
            String line;
            StringBuilder lineJoin = new StringBuilder();
            while ((line = buf.readLine()) != null) {
                lineJoin.append(line);
            }
            return lineJoin.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
