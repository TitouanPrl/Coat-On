package io.coaton.clothing.dto;

import jakarta.persistence.Id;
import lombok.*;

import java.math.BigInteger;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto { // NOTE: Any changes here must be reflected in `coaton-user`
    @Id
    @Getter
    private BigInteger id;
    @Getter @Setter
    private String username;
    @Getter @Setter
    private String email;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private String password;

    // TODO: Add a status field for Kafka events
}
