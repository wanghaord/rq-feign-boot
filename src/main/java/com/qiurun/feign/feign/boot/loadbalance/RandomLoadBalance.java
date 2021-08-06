package com.qiurun.feign.feign.boot.loadbalance;

import com.qiurun.feign.feign.boot.route.RouteInfo;

/**
 * @author wanghao
 * @Date 2021/8/5 6:43 下午
 * @Description
 * 随机轮询负载，在所有存活的服务中随机选择一台
 */
public class RandomLoadBalance implements ServiceLoadBalance {
    @Override
    public RouteInfo load(ServiceInfo serviceInfo) {
        return null;
    }
}
