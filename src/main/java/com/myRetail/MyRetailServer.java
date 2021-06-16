package com.myRetail;

import com.myRetail.clients.RedskyTargetClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
@SpringBootApplication
@EnableMongoRepositories
public class MyRetailServer {
    public static void main(String[] args) {
        SpringApplication.run(MyRetailServer.class, args);
        log.info("My Retail server is running.");
    }

    @Bean
    public RedskyTargetClient redskyTargetClient() { return new RedskyTargetClient(); }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
