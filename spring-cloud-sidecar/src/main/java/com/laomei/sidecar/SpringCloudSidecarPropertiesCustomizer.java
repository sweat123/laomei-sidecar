package com.laomei.sidecar;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.net.URI;

/**
 * 默认 spring cloud sidecar 是将被代理的服务注册到注册中心，在这里我们替换 spring cloud SidecarProperties
 * 配置，使用我们提供的配置，将 sidecar 本身注册到注册中心。
 * @author luobo.hwz on 2020/11/11 20:08
 */
public class SpringCloudSidecarPropertiesCustomizer implements ApplicationContextAware, BeanPostProcessor {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof org.springframework.cloud.netflix.sidecar.SidecarProperties) {
            final SidecarProperties sidecarProperties = applicationContext.getBean(SidecarProperties.class);
            ((org.springframework.cloud.netflix.sidecar.SidecarProperties) bean)
                    .setPort(sidecarProperties.getSidecarPort());
            final String host = sidecarProperties.getRedirectHost();
            final int port = sidecarProperties.getSidecarPort();
            final String healthAPI = sidecarProperties.getHealthAPI();
            final URI uri = new DefaultUriBuilderFactory().builder()
                    .host(host)
                    .port(port)
                    .path(healthAPI)
                    .build();
            ((org.springframework.cloud.netflix.sidecar.SidecarProperties) bean).setHealthUri(uri);
        }
        return bean;
    }
}
