package com.qiurun.feign.feign.boot.loadbalance;

import com.qiurun.feign.feign.boot.route.RouteInfo;

/**
 * @author wanghao
 * @Date 2021/8/5 5:23 下午
 * @Description
 * 负载策略：
 * 1、随机（从存活的服务列表中随机取一个服务进行调用）
 * 2、ip_hash 根据请求ip进行hash，确保同一个ip请求每次都可以路由到固定的机器上
 * 3、url_hash 根据请求url进行hash，相同请求url每次会落在同一台机器，适用于对某个频繁请求的url后端做结果缓存
 */
public interface ServiceLoadBalance {

    RouteInfo load(ServiceInfo serviceInfo);
}
