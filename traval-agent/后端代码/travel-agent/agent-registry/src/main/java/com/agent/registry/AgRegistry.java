package com.agent.registry;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class AgRegistry {
    public static void main(String[] args) {
        SpringApplication.run(AgRegistry.class);
    }
}
