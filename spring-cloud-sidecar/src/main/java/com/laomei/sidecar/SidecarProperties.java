package com.laomei.sidecar;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author luobo.hwz on 2020/11/11 20:03
 */
@Data
@ConfigurationProperties(prefix = "com.laomei.sidecar")
public class SidecarProperties {

    /**
     * header name
     */
    private String headerName;

    /**
     * header value
     */
    private String headerValue;

    /**
     * sidecar 代理的服务 host
     */
    private String redirectHost;

    /**
     * sidecar 代理的服务 port
     */
    private String redirectPort;

    /**
     * sidecar 代理的服务健康检查接口，需要返回结果 {"stauts":"UP"}
     */
    private String healthAPI;

    /**
     * sidecar 注册到 eureka 的端口，默认为 sidecar server port
     */
    @Value("${server.port}")
    private int    sidecarPort;
}
