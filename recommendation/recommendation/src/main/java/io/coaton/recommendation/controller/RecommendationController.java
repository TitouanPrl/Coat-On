package io.coaton.recommendation.controller;

import io.coaton.recommendation.dto.RecommendationActivityDto;
import io.coaton.recommendation.dto.RecommendationClothingDto;
import io.coaton.recommendation.model.Location;
import io.coaton.recommendation.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/recommendation") // NOTE: This mapping must be reflected in gateway's application.properties
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    @GetMapping("/activity")
    public RecommendationActivityDto getRecommendedActivity(@RequestParam(name="lat") String latitude,
                                                                      @RequestParam(name="long") String longitude,
                                                                      @RequestParam(name="range") String range,
                                                                      @RequestParam(name="start") String start,
                                                                      @RequestParam(name="end") String end) {
        return recommendationService.getRecommendedActivity(latitude, longitude, range, start, end);
    }

    @GetMapping("/clothing/current")
    public RecommendationClothingDto getRecommendedClothing(@RequestParam(name="lat") String latitude,
                                                                      @RequestParam(name="long") String longitude,
                                                                      @RequestParam(name="userId") Long userId) {
        return recommendationService.getClothingRecommendationCurrent(BigInteger.valueOf(userId), new Location(latitude, longitude));
    }
}
