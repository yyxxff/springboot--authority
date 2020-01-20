package com.maple.authority.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Configuration
public class BeanInitConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private ApplicationConfigProperties applicationConfigProperties;

    public static ApplicationConfigProperties jwtProperties;

    @PostConstruct
    public void init() {
        jwtProperties = this.applicationConfigProperties;
    }

}
