package com.github.iamhi.hizone.terreplein.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "hizone.service.auth")
@Data
public class AuthConfig {

    String username;

    String password;

    String baseUrl;

    String accessTokenName;
}
