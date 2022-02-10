package com.roy.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan("com.roy.order")
public class OrderProgramApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderProgramApplication.class, args);
    }
    
}
