package com.qiurun.feign.feign.boot.proxy;

import com.qiurun.feign.feign.boot.template.RqRequestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wanghao
 * @Date 2021/7/13 7:48 下午
 * @Description
 */
public class FeignMetaData {
    private String configKey;
    private List<String> formParams = new ArrayList();
    private RqRequestTemplate template;

    public RqRequestTemplate getTemplate() {
        return template;
    }

    public void setTemplate(RqRequestTemplate template) {
        this.template = template;
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public List<String> getFormParams() {
        return formParams;
    }

    public void setFormParams(List<String> formParams) {
        this.formParams = formParams;
    }
}
