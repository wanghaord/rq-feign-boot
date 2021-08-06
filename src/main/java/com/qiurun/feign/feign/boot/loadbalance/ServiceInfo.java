package com.qiurun.feign.feign.boot.loadbalance;

import com.qiurun.feign.feign.boot.template.RqRequestTemplate;

/**
 * @author wanghao
 * @Date 2021/8/5 5:48 下午
 * @Description
 */
public class ServiceInfo {
    // 服务id
    private String serviceId;
    // 服务名称
    private String serviceName;
    // 服务发起者ip
    private String requestAddr;
    // 服务发起者请求url
    private String requestUrl;

    private RqRequestTemplate template;

    public RqRequestTemplate getTemplate() {
        return template;
    }

    public void setTemplate(RqRequestTemplate template) {
        this.template = template;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getRequestAddr() {
        return requestAddr;
    }

    public void setRequestAddr(String requestAddr) {
        this.requestAddr = requestAddr;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }
}
