package com.agrihelp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class AgriHelpApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgriHelpApplication.class, args);
    }

    // Define RestTemplate bean so it can be injected into services
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
