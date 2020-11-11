package com.laomei.sidecar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.web.server.ServerWebExchange;

import java.util.function.Predicate;

/**
 * @author luobo.hwz on 2020/11/11 20:16
 */
public class SidecarHeaderRoutePredicateFactory
        extends AbstractRoutePredicateFactory<SidecarHeaderRoutePredicateFactory.Config> {


    public SidecarHeaderRoutePredicateFactory() {
        super(SidecarHeaderRoutePredicateFactory.Config.class);
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config c) {
        return exchange -> {
            final String value = exchange.getRequest().getHeaders().getFirst(c.header);
            return StringUtils.equals(value, c.value);
        };
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Config {
        private String header;
        private String value;
    }
}
