package io.coaton.clothing.model;

import java.math.BigInteger;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "clothing")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Clothing {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private BigInteger id;
    @NonNull
    private String name;
    @NonNull
    private ClothingType type;
    @NonNull // NOTE: User is defined in other service's module, can not use. Rely on userId instead
    private BigInteger userId;
    private String picture;
    @Builder.Default
    private Boolean usable = true;
    @Embedded
    private ClothingTags tags;
    @Enumerated(EnumType.STRING)
    private ClothingStatus status; // Used for Kafka events
}