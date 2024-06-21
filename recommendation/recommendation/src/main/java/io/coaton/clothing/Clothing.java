package io.coaton.clothing;

import java.math.BigInteger;

import jakarta.persistence.Embedded;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
public class Clothing {
    private BigInteger id;
    @Embedded
    @Builder.Default
    private ClothingType type = ClothingType.OTHER;
    private String name;
    @Embedded
    private ClothingTags tags;
}