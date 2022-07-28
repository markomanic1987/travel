package com.example.tasktravel;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class TaskTravelApplication
{

    public static void main( String[] args )
    {
        SpringApplication.run( TaskTravelApplication.class, args );
    }

    @Bean
    public RestTemplate restTemplate( RestTemplateBuilder builder) {
        return builder.build();
    }
}
