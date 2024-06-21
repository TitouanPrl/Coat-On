package io.coaton.recommendation.model;

import lombok.Data;
import java.util.List;

import io.coaton.clothing.Clothing;
import io.coaton.recommendation.dto.RecommendationClothingDto;

@Data
public class RecommendationClothing extends Recommendation {
    private List<Clothing> clothes;

    public RecommendationClothing(List<Clothing> clothes) {
        this.clothes = clothes;
    }

    public RecommendationClothingDto getRecommendationClothingDto() {
        return new RecommendationClothingDto(this.clothes);
    }

}
