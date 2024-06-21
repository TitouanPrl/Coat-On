package io.coaton.user.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.coaton.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import io.coaton.user.model.User;
import io.coaton.user.dto.UserDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public List<UserDto> getAllUsers() {
    List<User> users = new ArrayList<>();
    userRepository.findAll().forEach(users::add);
    return users.stream().map(this::mapToUserDto).toList();
  }

  private UserDto mapToUserDto(User user) {
    return UserDto.builder()
        .username(user.getUsername())
        .password(user.getPassword())
        .build();
  }

  public Optional<UserDto> getUser(BigInteger id) {
    Optional<User> user = userRepository.findById(id);
    return user.isPresent() ? user.map(this::mapToUserDto) : Optional.empty();
  }

  public User addUser(User user) throws Exception {
    try {
      User newUser = User.builder()
          .id(user.getId())
          .username(user.getUsername())
          .email(user.getEmail())
          .name(user.getName())
          .password(passwordEncoder.encode(user.getPassword()))
          .roles("USER")
          .build();
      userRepository.save(newUser);
      log.info("User {} is added to the Database", newUser.getId());
      return user;
    } catch (Exception e) {
      throw new Exception("User already exist");
    }
  }

  /*
   * public void updateUser(UserDto userDto, BigInteger id) throws Exception {
   * Optional<User> optionalUser = userRepository.findById(id);
   * if (optionalUser.isPresent()) {
   * User existingUser = optionalUser.get();
   * existingUser.setUsername(userDto.getUsername());
   * existingUser.setEmail(userDto.getEmail());
   * existingUser.setName(userDto.getName());
   * existingUser.setPassword(userDto.getPassword());
   * userRepository.save(existingUser);
   * log.info("User {} is updated.", existingUser.getId());
   * } else {
   * throw new Exception("User with ID " + userDto.getId() + " not found.");
   * }
   * }
   */

  public void deleteUser(BigInteger id) throws Exception {
    Optional<User> optionalUser = userRepository.findById(id);
    if (optionalUser.isPresent()) {
      userRepository.deleteById(id);
      log.info("User {} has been deleted.", id);
    } else {
      throw new Exception("User " + id + " does not exist.");
    }
  }

  private final KafkaTemplate<String, User> jsonKafkaTemplate;
  private final String userTopicJson = "userTopicJson";

  public void sendJsonToTopic(User user) {
    log.info("Log message - Send user json object to user topic: {} ", user.toString());
    jsonKafkaTemplate.send(userTopicJson, user);
  }
}
