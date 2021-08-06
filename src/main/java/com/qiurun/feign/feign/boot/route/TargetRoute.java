package com.qiurun.feign.feign.boot.route;

import java.util.LinkedList;

/**
 * @author wanghao
 * @Date 2021/8/5 3:57 下午
 * @Description
 */
public interface TargetRoute {
    RouteInfo route(String target);
    void registryRoute(LinkedList<RouteInfo> routeInfo);
}
