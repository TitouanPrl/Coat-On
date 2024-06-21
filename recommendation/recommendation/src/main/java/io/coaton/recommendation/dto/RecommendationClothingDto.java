package io.coaton.recommendation.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigInteger;
import java.util.List;

import io.coaton.clothing.Clothing;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationClothingDto {
    @Id
    @GeneratedValue
    private BigInteger id;
    private List<Clothing> clothes;

    public RecommendationClothingDto(List<Clothing> clothes) {
        this.clothes = clothes;
    }
}
