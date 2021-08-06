package com.qiurun.feign.feign.boot.loadbalance;

import com.qiurun.feign.feign.boot.route.RouteInfo;

/**
 * @author wanghao
 * @Date 2021/8/5 6:53 下午
 * @Description
 */
public class UrlHashLoadBalance implements ServiceLoadBalance {

    @Override
    public RouteInfo load(ServiceInfo serviceInfo) {
        System.out.println(serviceInfo.getServiceId() + "这是url hash");
        return null;
    }
}
