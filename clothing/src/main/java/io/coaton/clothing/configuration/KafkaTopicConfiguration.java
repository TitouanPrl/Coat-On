package io.coaton.clothing.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfiguration {

    @Bean
    public NewTopic ClothingTopicCreation() {
        return TopicBuilder.name(Topics.CLOTHING).build();
    }

    @Bean
    public NewTopic UserTopicCreation() {
        return TopicBuilder.name(Topics.USER).build();
    }

    public static class Topics {
        public final static String CLOTHING = "clothingTopic";
        public final static String USER = "userTopic";
    }
}
