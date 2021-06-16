package com.myRetail;

import com.github.cloudyrock.spring.v5.EnableMongock;
import com.myRetail.clients.RedskyTargetClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

@Slf4j
@EnableMongock
@SpringBootApplication
@EntityScan(basePackages = "com.myRetail.")
public class MyRetailServer {
    public static void main(String[] args) {
        SpringApplication.run(MyRetailServer.class, args);
        log.info("My Retail server is running.");
    }

    @Bean
    public RedskyTargetClient redskyTargetClient() { return new RedskyTargetClient(); }
}
