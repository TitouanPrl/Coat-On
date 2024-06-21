package io.coaton.clothing.dto;

import io.coaton.clothing.model.ClothingStatus;
import io.coaton.clothing.model.ClothingTags;
import io.coaton.clothing.model.ClothingType;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigInteger;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClothingDto {
    @Id
    private BigInteger id;
    @NonNull
    private String name;
    @NonNull
    private ClothingType type;
    @NonNull // NOTE: User is defined in other service's module, can not use. Rely on userId instead
    private BigInteger userId;
    private String picture;
    private Boolean usable;
    @Embedded
    private ClothingTags tags;
    @Enumerated(EnumType.STRING)
    private ClothingStatus status; // Used for Kafka events
}
