package com.qiurun.feign.feign.boot.proxy;

/**
 * @author wanghao
 * @Date 2021/7/13 6:43 下午
 * @Description
 */
public class ExtendFeignMethodHandler implements FeignMethodHandler {


    @Override
    public Object invoke(Object[] vars) {
        System.out.println("扩展实现");
        return null;
    }
}
