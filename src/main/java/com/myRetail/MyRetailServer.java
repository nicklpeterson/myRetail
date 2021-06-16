package com.myRetail;

import com.github.mongobee.Mongobee;
import com.myRetail.clients.RedskyTargetClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication()
@Slf4j
public class MyRetailServer {
    public static void main(String[] args) {
        SpringApplication.run(MyRetailServer.class, args);
        log.info("My Retail server is running.");
    }

    @Bean
    public RedskyTargetClient redskyTargetClient() { return new RedskyTargetClient(); }

    @Bean
    public Mongobee mongobee() {
        final Mongobee runner = new Mongobee("mongodb://dev:123@127.0.0.1:27017/my-retail-db\n");
        runner.setChangeLogsScanPackage("com.myRetail.changelogs");
        return runner;
    }
}
