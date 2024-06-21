package io.coaton.user.repository;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import io.coaton.user.model.User;

public interface UserRepository extends CrudRepository<User, BigInteger> {
    Optional<User> findByUsername(String username);

}
