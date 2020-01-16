package com.agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(scanBasePackages = "com.agent")
@MapperScan(value="com.agent.activity.mapper")
@EnableDiscoveryClient
public class AgActivityService {
    public static void main(String[] args) {
        SpringApplication.run(AgActivityService.class, args);
    }
}
