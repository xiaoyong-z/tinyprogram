package com.agent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(scanBasePackages = "com.agent")
@MapperScan(value="com.agent.service.mapper")
@EnableDiscoveryClient
public class AgServiceService {
    public static void main(String[] args) {
        SpringApplication.run(AgServiceService.class);
    }
}
