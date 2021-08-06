package com.qiurun.feign.feign.boot.proxy;

import com.qiurun.feign.feign.boot.template.RqRequestTemplate;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;


import java.io.IOException;
import java.util.Objects;

/**
 * @author wanghao
 * @Date 2021/7/13 6:42 下午
 * @Description
 */
public class DefaultFeignMethodHandler implements FeignMethodHandler {
    private Class<?> feignClient;
    private FeignMetaData metaData;

    public DefaultFeignMethodHandler(Class<?> feignClient, FeignMetaData metaData) {
        this.feignClient = feignClient;
        this.metaData = metaData;
    }

    @Override
    public Object invoke(Object[] vars) {
        RqRequestTemplate template = metaData.getTemplate();
        String method = template.getMethod();
        if (Objects.equals(method, "get")) {
            StringBuilder url = template.getUrl();
            for (Object var : vars) {
                url.append("=" + var);
            }
        }
        StringBuilder targetUrl = template.getUrl();
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(targetUrl.toString());
        CloseableHttpResponse response;
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(2000)
                .setSocketTimeout(5000)
                .setRedirectsEnabled(true)
                .build();
        httpGet.setConfig(config);

        // 执行
        String result = null;
        try {
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            result= EntityUtils.toString(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    static class Factory {
        public FeignMethodHandler create(Class target, FeignMetaData md) {
            return new DefaultFeignMethodHandler(target, md);
        }
    }
}
