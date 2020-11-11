package com.laomei.sidecar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author luobo.hwz on 2020/11/11 20:07
 */
@Configuration
@Import(org.springframework.cloud.netflix.sidecar.SidecarProperties.class)
@EnableConfigurationProperties(SidecarProperties.class)
public class SidecarConfiguration {

    @Autowired
    private SidecarProperties sidecarProperties;

    @Bean
    public SpringCloudSidecarPropertiesCustomizer springCloudSidecarPropertiesCustomizer() {
        return new SpringCloudSidecarPropertiesCustomizer();
    }

    @Bean
    public SidecarHeaderRoutePredicateFactory sidecarHeaderRoutePredicateFactory() {
        return new SidecarHeaderRoutePredicateFactory();
    }

    /**
     * 进入 sidecar 的请求如果存在我们配置的 header，并且 header 值和我们配置的预期值一样，
     * 说明这个请求需要被转发到第三方代理服务。
     * @param builder RouteLocatorBuilder
     * @return RouteLocator
     */
    @Bean
    public RouteLocator sidecarRouteLocator(final RouteLocatorBuilder builder) {
        final String uri = "http://" + sidecarProperties.getRedirectHost() + ":" +
                sidecarProperties.getRedirectPort();
        final SidecarHeaderRoutePredicateFactory.Config config =
                new SidecarHeaderRoutePredicateFactory.Config(
                        sidecarProperties.getHeaderName(), sidecarProperties.getHeaderValue());
        return builder.routes()
                .route(
                        p -> p.asyncPredicate(
                                sidecarHeaderRoutePredicateFactory()
                                    .applyAsync(config)
                        ).uri(uri)
                ).build();
    }
}
