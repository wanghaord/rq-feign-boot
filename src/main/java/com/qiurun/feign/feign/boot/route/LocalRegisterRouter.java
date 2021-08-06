package com.qiurun.feign.feign.boot.route;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wanghao
 * @Date 2021/8/5 4:00 下午
 * @Description
 * 根据服务名在本地注册表中获取目标服务信息
 * 1、获取结果分为两种，获取到则直接调用，获取不到则去zk寻找
 * 2、zk也获取不到则返回null
 */
public class LocalRegisterRouter implements TargetRoute {
    private static final Map<String, LinkedList<RouteInfo>> routeInfo = new ConcurrentHashMap<>();

    @Override
    public RouteInfo route(String target) {
        LinkedList<RouteInfo> routeInfos = routeInfo.get(target);

        return null;
    }

    @Override
    public void registryRoute(LinkedList<RouteInfo> routeInfo) {
        for (RouteInfo route : routeInfo) {

        }
    }
}
