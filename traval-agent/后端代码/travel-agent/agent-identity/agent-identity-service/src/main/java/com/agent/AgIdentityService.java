package com.agent;


import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@MapperScan(value = "com.agent.identity.mapper")
@EnableDiscoveryClient
public class AgIdentityService {
    public static void main(String[] args) {
        SpringApplication.run(AgIdentityService.class, args);
    }
}
