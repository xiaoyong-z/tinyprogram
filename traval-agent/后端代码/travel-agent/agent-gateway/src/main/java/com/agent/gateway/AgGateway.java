package com.agent.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication(scanBasePackages="com.agent")
@EnableDiscoveryClient
public class AgGateway {
    public static void main(String[] args) {
        SpringApplication.run(AgGateway.class);
    }
}
