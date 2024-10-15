package org.example.framework.security.core.requestwrapper;

import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.example.framework.utils.XssUtil;

import java.util.LinkedHashMap;
import java.util.Map;

public class XssRequestWrapper extends HttpServletRequestWrapper {

    public XssRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        return filterXss(value);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] parameters = super.getParameterValues(name);
        if (parameters == null || parameters.length == 0) {
            return null;
        }
        for (int i = 0; i < parameters.length; i++) {
            parameters[i] = filterXss(parameters[i]);
        }
        return parameters;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> result = new LinkedHashMap<>();
        Map<String, String[]> parameters = super.getParameterMap();
        for (String key : parameters.keySet()) {
            String[] values = parameters.get(key);
            for (int i = 0; i < values.length; i++) {
                values[i] = filterXss(values[i]);
            }
            result.put(key, values);
        }
        return result;
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        return filterXss(value);
    }

    //@Override
    //public StringBuffer getRequestURL() {
    //    return new StringBuffer(filterXss(super.getRequestURL().toString()));
    //}
    //
    //@Override
    //public String getRequestURI() {
    //    return filterXss(super.getRequestURI());
    //}

    private String filterXss(String content) {
        if (StrUtil.isBlank(content)) {
            return content;
        }
        return XssUtil.filter(content);
    }
}
