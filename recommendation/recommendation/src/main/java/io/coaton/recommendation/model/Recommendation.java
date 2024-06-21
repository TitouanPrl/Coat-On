package io.coaton.recommendation.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
public class Recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private BigInteger id;
    // Recommendation data
    private final LocalDateTime madeAt = LocalDateTime.now();

}
