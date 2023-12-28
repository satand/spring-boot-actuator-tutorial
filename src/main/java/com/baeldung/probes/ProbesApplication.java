package com.baeldung.probes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProbesApplication {

    public static void main(String[] args) {
        // only load properties for this application
        System.setProperty("spring.config.location", "classpath:application-probes.properties");
        SpringApplication.run(ProbesApplication.class, args);
    }
}
