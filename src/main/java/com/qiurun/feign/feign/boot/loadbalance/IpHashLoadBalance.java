package com.qiurun.feign.feign.boot.loadbalance;

import com.qiurun.feign.feign.boot.route.RouteInfo;

/**
 * @author wanghao
 * @Date 2021/8/5 6:52 下午
 * @Description
 */
public class IpHashLoadBalance implements ServiceLoadBalance {
    @Override
    public RouteInfo load(ServiceInfo serviceInfo) {
        System.out.println("这是ip hash");
        return null;
    }
}
