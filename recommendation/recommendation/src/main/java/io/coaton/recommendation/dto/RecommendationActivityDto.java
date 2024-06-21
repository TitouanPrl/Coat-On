package io.coaton.recommendation.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigInteger;

import io.coaton.recommendation.dto.activity.ActivityDto;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationActivityDto {
    @Id
    @GeneratedValue
    private BigInteger id;
    private List<ActivityDto> activities;

    public RecommendationActivityDto(List<ActivityDto> activities) {
        this.activities = activities;
    }

}
