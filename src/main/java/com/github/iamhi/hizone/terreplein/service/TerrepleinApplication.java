package com.github.iamhi.hizone.terreplein.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication(scanBasePackages = {"com.github.iamhi.hizone.terreplein"})
@ConfigurationPropertiesScan("com.github.iamhi.hizone.terreplein.config")
@EnableR2dbcRepositories(basePackages = {"com.github.iamhi.hizone.terreplein.out"})
public class TerrepleinApplication {

    public static void main(String[] args) {
        SpringApplication.run(TerrepleinApplication.class, args);
    }

}
