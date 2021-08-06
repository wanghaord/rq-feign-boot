package com.qiurun.feign.feign.boot.route;

/**
 * @author wanghao
 * @Date 2021/8/5 4:02 下午
 * @Description
 * 路由表信息，同一个服务可以在集群中存在多个实例，即会有多个RouteInfo
 */
public class RouteInfo {
    // 服务id
    private String serviceId;
    // 服务名称
    private String serviceName;
    // 服务ip地址
    private String serviceAddr;
    // 服务是否存活
    private boolean isAlive;
    // 服务在线时长
    private long aliveTimes;

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

    public String getServiceAddr() {
        return serviceAddr;
    }

    public void setServiceAddr(String serviceAddr) {
        this.serviceAddr = serviceAddr;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public long getAliveTimes() {
        return aliveTimes;
    }

    public void setAliveTimes(long aliveTimes) {
        this.aliveTimes = aliveTimes;
    }
}
