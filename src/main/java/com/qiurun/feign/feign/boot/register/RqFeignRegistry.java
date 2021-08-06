package com.qiurun.feign.feign.boot.register;

import com.qiurun.feign.feign.boot.annotations.MyFeign;
import com.qiurun.feign.feign.boot.annotations.RqEnableFeign;
import com.qiurun.feign.feign.boot.factory.CustomerFeignFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author wanghao
 * @Date 2021/7/7 11:46 上午
 * @Description
 */
public class RqFeignRegistry implements ImportBeanDefinitionRegistrar, BeanClassLoaderAware, ResourceLoaderAware, EnvironmentAware, BeanFactoryAware {

    private ResourceLoader resourceLoader;
    private Environment environment;
    private BeanFactory beanFactory;
    private ClassLoader classLoader;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        try {
            this.registryFeignClient(metadata, registry);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 注册标注了FeignClient注解的接口代理类到spring容器
     * @param metadata
     * @param registry
     * @throws ClassNotFoundException
     */
    public void registryFeignClient(AnnotationMetadata metadata, BeanDefinitionRegistry registry) throws ClassNotFoundException {
        ClassPathScanningCandidateComponentProvider scanner = this.getScanner();
        scanner.setResourceLoader(resourceLoader);
        AnnotationTypeFilter annotationTypeFilter = new AnnotationTypeFilter(MyFeign.class);
        scanner.addIncludeFilter(annotationTypeFilter);
        Object basePackages;
        basePackages = this.getBasePackages(metadata);
        Iterator basePackage = ((Set)basePackages).iterator();
        while (basePackage.hasNext()) {
            String pack = (String) basePackage.next();
            Set<BeanDefinition> candidateComponents = scanner.findCandidateComponents(pack);
            Iterator<BeanDefinition> candidate = candidateComponents.iterator();
            while (candidate.hasNext()) {
                BeanDefinition candidateBeanDefinition = candidate.next();
                if (candidateBeanDefinition instanceof AnnotatedBeanDefinition) {
                    AnnotatedBeanDefinition beanDefinition = (AnnotatedBeanDefinition) candidateBeanDefinition;
                    String beanClassName = candidateBeanDefinition.getBeanClassName();
                    Proxy proxy = CustomerFeignFactory.newInstance(beanDefinition);
                    this.registerProxyClient(beanClassName, proxy);
                }
            }
        }
    }

    private void registerProxyClient(String bean, Proxy proxy) {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) this.beanFactory;
        beanFactory.registerSingleton(bean, proxy);
    }

    String getName(Map<String, Object> attributes) {
        String name = (String)attributes.get("serviceId");
        if (!StringUtils.hasText(name)) {
            name = (String)attributes.get("name");
        }

        if (!StringUtils.hasText(name)) {
            name = (String)attributes.get("value");
        }

        name = this.resolve(name);
        if (!StringUtils.hasText(name)) {
            return "";
        } else {
            String host = null;

            try {
                String url;
                if (!name.startsWith("http://") && !name.startsWith("https://")) {
                    url = "http://" + name;
                } else {
                    url = name;
                }

                host = (new URI(url)).getHost();
            } catch (URISyntaxException var5) {
            }

            Assert.state(host != null, "Service id not legal hostname (" + name + ")");
            return name;
        }
    }

    private String resolve(String value) {
        return StringUtils.hasText(value) ? this.environment.resolvePlaceholders(value) : value;
    }

    public ClassPathScanningCandidateComponentProvider getScanner() {
        return new ClassPathScanningCandidateComponentProvider(false, this.environment) {
            protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                boolean isCandidate = false;
                if (beanDefinition.getMetadata().isIndependent() && !beanDefinition.getMetadata().isAnnotation()) {
                    isCandidate = true;
                }
                return isCandidate;
            }
        };
    }

    protected Set<String> getBasePackages(AnnotationMetadata metadata) {
        Map<String, Object> attributes = metadata.getAnnotationAttributes(RqEnableFeign.class.getCanonicalName());
        String[] var4 = (String[])((String[])attributes.get("value"));
        Set<String> basePackages = new HashSet();
        int var5 = var4.length;

        int var6;
        String pkg;
        for(var6 = 0; var6 < var5; ++var6) {
            pkg = var4[var6];
            if (StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }

        var4 = (String[])((String[])attributes.get("basePackages"));
        var5 = var4.length;

        for(var6 = 0; var6 < var5; ++var6) {
            pkg = var4[var6];
            if (StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }

        Class[] var8 = (Class[])((Class[])attributes.get("basePackageClasses"));
        var5 = var8.length;

        for(var6 = 0; var6 < var5; ++var6) {
            Class<?> clazz = var8[var6];
            basePackages.add(ClassUtils.getPackageName(clazz));
        }

        if (basePackages.isEmpty()) {
            basePackages.add(ClassUtils.getPackageName(metadata.getClassName()));
        }

        return basePackages;
    }


    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory =  beanFactory;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}
