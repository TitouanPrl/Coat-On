package io.coaton.clothing;

import jakarta.persistence.Embedded;
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
}
