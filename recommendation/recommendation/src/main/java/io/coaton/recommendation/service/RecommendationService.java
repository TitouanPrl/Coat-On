package io.coaton.recommendation.service;

import io.coaton.clothing.Clothing;
import io.coaton.recommendation.dto.RecommendationActivityDto;
import io.coaton.recommendation.dto.RecommendationClothingDto;
import io.coaton.recommendation.dto.WeatherDto;
import io.coaton.recommendation.dto.activity.ActivityDto;
import io.coaton.recommendation.model.Location;
import io.coaton.weather.Weather;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
@RequiredArgsConstructor
public class RecommendationService {
        @Autowired
        private WebClient.Builder webClientBuilder;
        @Autowired
        private ClothingRecommendationService clothingRecommendationService = new ClothingRecommendationService(
                        webClientBuilder);

        public RecommendationActivityDto getRecommendedActivity(String latitude, String longitude,
                        String range,
                        String startDateTime, String endDateTime) {
                Map<String, Double> locationData = ActivityRecommendationService.parseLocationValues(latitude,
                                longitude,
                                range);
                Map<String, LocalDateTime> timeData = ActivityRecommendationService.parseTimeValues(startDateTime,
                                endDateTime);

                WeatherDto weather = getWeatherForTime(new Location(latitude, longitude),
                                timeData.get("start").plus(
                                                Duration.between(timeData.get("start"), timeData.get("end"))
                                                                .dividedBy(2)));

                List<ActivityDto> activities = getActivityDtos(new Location(latitude, longitude),
                                locationData.get("radius"));

                Optional<ActivityDto> bestMatch = ActivityRecommendationService.recommendActivity(locationData,
                                timeData,
                                weather, activities);

                List<ActivityDto> recommendedActivities = new ArrayList<>();
                if (bestMatch.isPresent()) {
                        recommendedActivities.add(bestMatch.get());
                }

                return RecommendationActivityDto.builder().activities(recommendedActivities).build();
        }

        public RecommendationClothingDto getClothingRecommendationCurrent(BigInteger userId, Location location) {
                List<Clothing> clothes = clothingRecommendationService.getWardrobe(userId);
                Weather weather = getCurrentWeather(location);
                return clothingRecommendationService.getFullBodyRecommendation(weather, clothes)
                                .getRecommendationClothingDto();
        }

        public WeatherDto getCurrentWeatherDto(Location location) {
                String res = webClientBuilder.build().get()
                                .uri("http://localhost:8087/api/weather/current/lat={latitude}&lon={longitude}",
                                                location.getLat(),
                                                location.getLon())
                                .retrieve()
                                .bodyToMono(String.class).block();

                return new WeatherDto(res);
        }

        public WeatherDto getWeatherForTime(Location location, LocalDateTime time) {
                String res = webClientBuilder.build().get()
                                .uri("http://localhost:8087/api/weather?lat={latitude}&lon={longitude}&time={time}",
                                                location.getLat(),
                                                location.getLon(),
                                                time.format(ActivityRecommendationService.DATE_TIME_FORMATTER))
                                .retrieve()
                                .bodyToMono(String.class).block();

                return new WeatherDto(res);
        }

        public Weather getCurrentWeather(Location location) {
                return new Weather(getCurrentWeatherDto(location));
        }

        public List<ActivityDto> getActivityDtos(Location location, Double radius) {
                String activitiesResult = webClientBuilder
                                .build()
                                .get()
                                .uri("http://localhost:8088/api/activity?lat=" + location.getLat()
                                                + "&long=" + location.getLon()
                                                + "&radius=" + radius)
                                .retrieve()
                                .bodyToMono(String.class)
                                .block();
                return ActivityRecommendationService.parseActivities(activitiesResult);
        }

}
