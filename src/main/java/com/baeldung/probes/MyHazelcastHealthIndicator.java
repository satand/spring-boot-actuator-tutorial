package com.baeldung.probes;

import com.hazelcast.core.HazelcastInstance;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.logging.Logger;

@Component
public class MyHazelcastHealthIndicator implements HealthIndicator {

    private static final Logger log = Logger.getLogger(MyCustomHealthIndicator.class.getSimpleName());

    private final HazelcastInstance hazelcast;

    public MyHazelcastHealthIndicator(HazelcastInstance hazelcast) {
        Assert.notNull(hazelcast, "HazelcastInstance must not be null");
        this.hazelcast = hazelcast;
    }

    @Override
    public Health health() {
        log.info("Invoked check of MyHazelcastHealthIndicator");

        try {
            return this.hazelcast.executeTransaction((context) -> Health.up()
                    .withDetail("client", this.hazelcast.getName() + "(" + this.hazelcast.getLocalEndpoint().getUuid().toString() + ")")
                    .withDetail("map data size", this.hazelcast.getMap("data").size())
                    .build());
        } catch (Exception e) {

            return Health.down().withDetail("error", e.getMessage()).build();
        }
    }
}