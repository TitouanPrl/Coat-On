package io.coaton.notification.service;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import io.coaton.notification.dto.UserDto;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@Service
public class NotificationService {

  @KafkaListener(topics = "userTopicJson", groupId = "userEventGroup" )
  public void consumeUser(UserDto user){
      log.info("Log message recieved from user topic: {} ", user.toString());
  }

  /* Add more listener based on the number of events
   * Add the topics and group in the application.properties
   */
}
