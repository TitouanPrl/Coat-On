package io.coaton.user.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import io.coaton.user.service.UserService;
import io.coaton.user.dto.UserDto;
import io.coaton.user.jwt.JwtService;
import io.coaton.user.model.User;

import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins = "*")
@Slf4j
@RequestMapping("/api")
public class UserController {
  @Autowired
  private UserService userService;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtService jwtService;

  @GetMapping("/user")
  public List<UserDto> getAllUsers() {
    return userService.getAllUsers();
  }

  @PostMapping("/user")
  public void addUser(@RequestBody User user) {
    try {
      userService.addUser(user);
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
    } finally {
      userService.sendJsonToTopic(user);
    }
  }

  @GetMapping("/user/{id}")
  public Optional<UserDto> getUser(@PathVariable BigInteger id) {
    Optional<UserDto> user = userService.getUser(id);
    if (user.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }
    return user;
  }

  /*
   * @PutMapping("/user/{id}")
   * public void updateUser(@RequestBody UserDto userDto, @PathVariable BigInteger
   * id) {
   * try {
   * userService.updateUser(userDto, id);
   * } catch (Exception e) {
   * log.error(e.getMessage());
   * throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
   * } finally {
   * userService.sendJsonToTopic(userService.getUser(id).get());
   * }
   * }
   */
  /*
   * @DeleteMapping("/user/{id}")
   * public void deleteUser(@PathVariable BigInteger id) {
   * try {
   * userService.sendJsonToTopic(userService.getUser(id).get());
   * userService.deleteUser(id);
   * } catch (Exception e) {
   * log.error(e.getMessage());
   * throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
   * }
   * }
   */

  @GetMapping("/authenticate")
  public Boolean authenticate(@RequestHeader("Authorization") String header) {
    String token = header.replace("Bearer ", "");
    log.info(" authenticate - token {} ", token);
    return jwtService.validateToken(token);
  }

  // an end point for signing up new users
  @PostMapping("/signup")
  public String signupUser(@RequestBody User user) throws Exception {
    userService.addUser(user);
    String jwtToken = jwtService.generateToken(user.getUsername());
    return jwtToken;
  }

  @PostMapping("/login")
  public String logInAndGetToken(@RequestBody UserDto userDto) {
    log.info("MADE IT HERE", userDto.toString());

    if (userDto.getUsername() == null || userDto.getPassword() == null) {
      throw new UsernameNotFoundException("UserName or Password is Empty");
    }

    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));
    // If the user is authenticated we generate the token, otherwise, we throw an
    // exception
    // log.info("authentication.isAuthenticated() {} ", authentication);

    if (authentication.isAuthenticated()) {
      log.info("jwtService.generateToken(authRequest.getUsername())  {} ",
          jwtService.generateToken(userDto.getUsername()));
      return jwtService.generateToken(userDto.getUsername());
    } else {
      throw new UsernameNotFoundException("The user cannot be authinticated!");
    }
  }

}
