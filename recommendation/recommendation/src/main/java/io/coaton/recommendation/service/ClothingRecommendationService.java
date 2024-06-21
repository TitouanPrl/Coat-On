package io.coaton.recommendation.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import io.coaton.clothing.Clothing;
import io.coaton.clothing.ClothingHelper;
import io.coaton.clothing.ClothingTags;
import io.coaton.recommendation.model.RecommendationClothing;
import io.coaton.weather.Weather;

@Service
public class ClothingRecommendationService {
    static final int BEST_SCORE = 3;

    private WebClient.Builder webClientBuilder;

    public ClothingRecommendationService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public List<Clothing> getWardrobe(BigInteger userId) {
        String res = webClientBuilder.build().get()
                .uri("http://localhost:8083/api/user/{userId}/clothing", userId.toString()).retrieve()
                .bodyToMono(String.class).block();
        JSONArray jsonArray = new JSONArray(res);

        List<Clothing> wardrobe = new ArrayList<>();

        jsonArray.forEach(o -> wardrobe.add(ClothingHelper.mapDtoToClothing((JSONObject) o)));
        return wardrobe;
    }

    public Optional<Clothing> createClothingRecommendation(Weather weather, List<Clothing> clothes) {
        int maxScore = 0;
        Optional<Clothing> bestItem = Optional.ofNullable(null);
        ClothingTags weatherTags = weather.getTags();

        for (Clothing item : clothes) {
            ClothingTags itemTags = item.getTags();
            int score = 0;

            if (itemTags.getTemperature() == weatherTags.getTemperature()) {
                score = score + 1;
            }

            if (itemTags.getWindResistance() == weatherTags.getWindResistance()) {
                score = score + 1;
            }

            if (itemTags.getPrecipitationProtection() == weatherTags.getPrecipitationProtection()) {
                score = score + 1;
            }

            if (score >= BEST_SCORE) {
                bestItem = Optional.of(item);
                return bestItem;
            } else if (score > maxScore) {
                maxScore = score;
                bestItem = Optional.of(item);
            }

        }
        return bestItem;
    }

    public RecommendationClothing getFullBodyRecommendation(Weather weather, List<Clothing> clothes) {
        List<Optional<Clothing>> recommendedClothes = new ArrayList<>();

        recommendedClothes.add(createClothingRecommendation(weather, ClothingHelper.getTorsoClothing(clothes)));
        recommendedClothes.add(createClothingRecommendation(weather, ClothingHelper.getLegsClothing(clothes)));
        recommendedClothes.add(createClothingRecommendation(weather, ClothingHelper.getHeadClothing(clothes)));
        recommendedClothes.add(createClothingRecommendation(weather, ClothingHelper.getFeetClothing(clothes)));
        recommendedClothes.add(createClothingRecommendation(weather, ClothingHelper.getHandsClothing(clothes)));

        return new RecommendationClothing(recommendedClothes.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList()));
    }





}
