package com.qiurun.feign.feign.boot.factory;

import com.qiurun.feign.feign.boot.annotations.MyFeign;
import com.qiurun.feign.feign.boot.annotations.RqParam;
import com.qiurun.feign.feign.boot.annotations.RqRequestMapping;
import com.qiurun.feign.feign.boot.proxy.DefaultFeignMethodHandler;
import com.qiurun.feign.feign.boot.proxy.FeignMetaData;
import com.qiurun.feign.feign.boot.proxy.FeignMethodHandler;
import com.qiurun.feign.feign.boot.proxy.FeignProxy;
import com.qiurun.feign.feign.boot.template.RqRequestTemplate;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wanghao
 * @Date 2021/7/13 6:47 下午
 * @Description
 */
public class CustomerFeignFactory {

    public static <T> T newInstance(AnnotatedBeanDefinition beanDefinition) {
        AnnotationMetadata metadata = beanDefinition.getMetadata();
        Map<Method, FeignMethodHandler> dispatch = new ConcurrentHashMap<>();
        Class<?> clazz = null;
        try {
            clazz = Class.forName(metadata.getClassName());
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                FeignMetaData md = resolveMetaData(clazz, method);
                dispatch.put(method, new DefaultFeignMethodHandler(clazz, md));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        InvocationHandler handler =  new FeignProxy(clazz, dispatch);
        T proxy = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, handler);
        return proxy;
    }

    /**
     * 解析源接口信息，封装请求模板
     * @param target
     * @param method
     * @return
     */
    private static FeignMetaData resolveMetaData(Class<?> target, Method method) {
        FeignMetaData metaData = new FeignMetaData();
        metaData.setConfigKey(method.getName() + "#");
        if (target.isAnnotationPresent(MyFeign.class)) {
            MyFeign feign = target.getAnnotation(MyFeign.class);
            String host = feign.name();
            Assert.hasText(host, "MyFeign Annotation name field can't null");
            RqRequestTemplate requestTemplate = new RqRequestTemplate();
            StringBuilder builder = new StringBuilder();
            builder.append("http://");
            builder.append(host);
            if (method.isAnnotationPresent(RqRequestMapping.class)) {
                RqRequestMapping rqRequestMapping = method.getAnnotation(RqRequestMapping.class);
                String requestMethod = rqRequestMapping.method();
                requestTemplate.setMethod(requestMethod);
                String path = rqRequestMapping.value();
                Assert.hasText(path, "RqMapping can't not null");
                builder.append(path);
                requestTemplate.setUrl(builder);
            }

            metaData.setTemplate(requestTemplate);
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            int count = parameterAnnotations.length;
            for (int i = 0; i < count; i++) {
                if (parameterAnnotations[i] != null) {
                    processAnnotationOnParam(metaData, parameterAnnotations[i], i);
                }
            }

        }
        return metaData;
    }

    private static void processAnnotationOnParam(FeignMetaData data, Annotation[] annotations, int paramIndex) {
        Annotation[] var1 = annotations;
        int var2 = annotations.length;
        for (int var3 = 0; var3 < var2; var3++) {
            Annotation annotation = var1[var3];
            Class<? extends Annotation> annotationType = annotation.annotationType();
            if (annotationType == RqParam.class) {
                RqParam param = (RqParam) annotation;
                String value = param.value();
                RqRequestTemplate template = data.getTemplate();
                if (Objects.equals(data.getTemplate().getMethod(), "get")) {
                    template.getUrl().append("?" + value);
                }
            }
        }
    }

    private static RqRequestTemplate buildRequestTemplate(Class<?> target) {
        return null;
    }


}
