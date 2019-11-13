package com.market.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication(scanBasePackages="com.market")
@EnableDiscoveryClient
public class mkGateway {
    public static void main(String[] args) {
        SpringApplication.run(mkGateway.class);
    }
}

