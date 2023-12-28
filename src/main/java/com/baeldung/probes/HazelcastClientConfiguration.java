package com.baeldung.probes;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientConnectionStrategyConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration(proxyBeanMethods = false)
public class HazelcastClientConfiguration {

    @Bean
    @Profile("test")
    public HazelcastInstance testHazelcastInstance() {

        return Hazelcast.newHazelcastInstance();
    }

    @Bean
    @Profile("!test")
    public HazelcastInstance createHazelcastInstance(@Value("${hazelcast.clusterName}") String clusterName) {

        ClientConfig config = new ClientConfig();

        config.setClusterName(clusterName);

        config.getNetworkConfig().setConnectionTimeout(1000);

        // Start client connection to cluster in sync mode,
        // but restore lost connection in async mode
        // (this returns immediately a failure to HazelcastHealthIndicator)
        config.getConnectionStrategyConfig()
                .setAsyncStart(false)
                .setReconnectMode(ClientConnectionStrategyConfig.ReconnectMode.ASYNC);

        return HazelcastClient.newHazelcastClient(config);
    }



}

