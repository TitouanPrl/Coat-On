package io.coaton.notification.model;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
  private BigInteger id;
  private String username;
  private String email;
  private String name;
  private String password;
}
