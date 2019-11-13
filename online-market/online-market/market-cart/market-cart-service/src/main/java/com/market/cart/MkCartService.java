package com.market.cart;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(scanBasePackages="com.market")
@EnableDiscoveryClient
@MapperScan("com.market.cart.mapper")
public class MkCartService {
    public static void main(String[] args) {
        SpringApplication.run(MkCartService.class);
    }
}
