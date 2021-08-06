package com.qiurun.feign.feign.boot.template;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author wanghao
 * @Date 2021/7/19 2:03 下午
 * @Description
 */
public class RqRequestTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Map<String, Collection<String>> query = new LinkedHashMap<>();
    private final Map<String, Collection<String>> headers = new LinkedHashMap<>();
    private String method;
    private StringBuilder url = new StringBuilder();
    private byte[] body;

    public RqRequestTemplate() {
    }

    public RqRequestTemplate(String method, StringBuilder url, byte[] body) {
        this.method = method;
        this.url = url;
        this.body = body;
    }

    public Map<String, Collection<String>> getQuery() {
        return query;
    }

    public Map<String, Collection<String>> getHeaders() {
        return headers;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public StringBuilder getUrl() {
        return url;
    }

    public void setUrl(StringBuilder url) {
        this.url = url;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}
