package com.qiurun.feign.feign.boot.proxy;

/**
 * @author wanghao
 * @Date 2021/7/13 6:38 下午
 * @Description
 */
public interface FeignMethodHandler {
    Object invoke(Object[] vars);
}
