package com.baeldung.probes;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class MyCustomHealthIndicator implements HealthIndicator {

    private static final Logger log = Logger.getLogger(MyCustomHealthIndicator.class.getSimpleName());

    @Override
    public Health health() {
        int errorCode = check();
        if (errorCode != 0) {
            return Health.down().withDetail("Error Code", errorCode).build();
        }
        return Health.up().build();
    }

    private int check() {
        log.info("Invoked check of MyCustomHealthIndicator");
        // perform some specific health check
        return 0;
    }

}

