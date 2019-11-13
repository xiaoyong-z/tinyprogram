package com.market.history;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(scanBasePackages="com.market")
@EnableDiscoveryClient
@MapperScan("com.market.history.mapper")
public class MkHistoryService {
    public static void main(String[] args) {
        SpringApplication.run(MkHistoryService.class);
    }
}
