package com.hackathon.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

    /**
     * Use "dev" profile for development
     * Use "prod" profile for production
     */
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

}
