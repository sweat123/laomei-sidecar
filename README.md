基于 `spring cloud gateway` 实现的 `sidecar`。

相比于`spring cloud`提供的`sidecar`，不再将被代理服务注册到`eureka`，而是将`sidecar`本身注册到`eureka`。

1. 请求从其他微服务发往被代理服务时，先走服务发现，找到`sidecar`，`sidecar`根据`header`判断请求来源，转发到被代理服务。
2. 请求从被代理服务发往其他微服务时，被代理服务直接向`sidecar`发送请求，`url`前缀为需要被调用的微服务实例名，例如`/{serviceName}/api/xxx`，`serviceName`为微服务在`eureka`注册的名字。`sidecar`会通过`spring cloud gateway`转发请求到微服务。

为了能够区分请求来自微服务还是被代理服务，我通过`header`来区分请求来源，用户可以自定义`header`配置，来区分请求来源。

下面是样例配置：

```yaml
com:
  laomei:
    sidecar:
      header-name: headerA
      header-value: valueA
      redirect-host: localhost
      redirect-port: 9005 # 被代理服务的端口
      sidecar-port: 8080 #当前sidecar的端口
      health-a-p-i: /healthz

server:
  port: 8080

spring:
  application:
    name: example
  cloud:
    gateway:
      discovery:
        locator:
          enabled: true
eureka:
  client:
    service-url:
      defaultZone: 'http://localhost:8761/eureka/'

```

具体可以参考`example`项目。
