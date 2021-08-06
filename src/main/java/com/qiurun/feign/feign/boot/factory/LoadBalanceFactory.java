package com.qiurun.feign.feign.boot.factory;



import com.qiurun.feign.feign.boot.enums.LoadBalanceEnum;
import com.qiurun.feign.feign.boot.loadbalance.IpHashLoadBalance;
import com.qiurun.feign.feign.boot.loadbalance.RandomLoadBalance;
import com.qiurun.feign.feign.boot.loadbalance.ServiceInfo;
import com.qiurun.feign.feign.boot.loadbalance.UrlHashLoadBalance;
import com.qiurun.feign.feign.boot.route.RouteInfo;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author wanghao
 * @Date 2021/8/5 8:20 下午
 * @Description
 */
public class LoadBalanceFactory {
    private static Map<LoadBalanceEnum, Function<ServiceInfo, RouteInfo>> checkResultDispatcher = new LinkedHashMap<>();

    static {
       checkResultDispatcher.put(LoadBalanceEnum.IP_HASH, serviceInfo -> new IpHashLoadBalance().load(serviceInfo));
       checkResultDispatcher.put(LoadBalanceEnum.URL_HASH, serviceInfo -> new UrlHashLoadBalance().load(serviceInfo));
       checkResultDispatcher.put(LoadBalanceEnum.RANDOM, serviceInfo -> new RandomLoadBalance().load(serviceInfo));
    }

    public static void process(LoadBalanceEnum strategyId) {
        Function<ServiceInfo, RouteInfo> function = checkResultDispatcher.get(strategyId);
        ServiceInfo serviceInfo = new ServiceInfo();
        if (function != null) {
            function.apply(serviceInfo);
        }
    }

}
