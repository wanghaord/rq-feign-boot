package com.qiurun.feign.feign.boot.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author wanghao
 * @Date 2021/7/13 6:27 下午
 * @Description
 */
public class FeignProxy implements InvocationHandler {
    private final Class<?> feignClient;
    private final Map<Method, FeignMethodHandler> dispatch;

    public FeignProxy(Class<?> feignClient, Map<Method, FeignMethodHandler> dispatch) {
        this.feignClient = feignClient;
        this.dispatch = dispatch;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return this.dispatch.get(method).invoke(args);
    }
}
