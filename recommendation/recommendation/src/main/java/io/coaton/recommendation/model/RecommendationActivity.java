package io.coaton.recommendation.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "activity")
@Builder(builderMethodName = "recommendationActivityBuilder")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationActivity extends Recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private BigInteger id;

    // TODO: Add any other fields or values that might be needed
}
