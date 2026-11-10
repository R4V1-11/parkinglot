package com.example.parkinglot.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.stereotype.Component;

@Component
public class KafkaConfig {

    public static final String PARKED = "vehicle_parked";
    public static final String EXITED = "vehicle_exited";

    @Bean
    public NewTopic vehicleParkedTopic() {
        return TopicBuilder.name(PARKED)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic vehicleExitedTopic() {
        return TopicBuilder.name(EXITED)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
