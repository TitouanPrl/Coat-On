package io.coaton.recommendation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.coaton.clothing.Clothing;
import io.coaton.clothing.ClothingType;
import io.coaton.clothing.ClothingTags;
import io.coaton.recommendation.service.ClothingRecommendationService;
import io.coaton.weather.Weather;

@SpringBootTest
public class ClothingRecommendationTests {
        @Autowired
        private WebClient.Builder webClientBuilder;

        @Autowired
        private ClothingRecommendationService recommendationCreator = new ClothingRecommendationService(
                        webClientBuilder);

        private List<Clothing> createColdWetTorsoWardrobe() {
                List<Clothing> clothes = new ArrayList<>();

                clothes.add(new Clothing(
                                BigInteger.valueOf(1),
                                ClothingType.COAT,
                                "Name 1",

                                new ClothingTags(
                                                ClothingTags.Temperature.COLD,
                                                ClothingTags.PrecipitationProtection.HEAVY_RAIN,
                                                ClothingTags.WaterResistance.HIGH,
                                                ClothingTags.WindResistance.WINDY,
                                                ClothingTags.BodySection.TORSO_OUTER)));

                clothes.add(new Clothing(
                                BigInteger.valueOf(2),
                                ClothingType.JACKET,
                                "Name 2",

                                new ClothingTags(
                                                ClothingTags.Temperature.CHILLY,
                                                ClothingTags.PrecipitationProtection.SNOWFALL,
                                                ClothingTags.WaterResistance.HIGH,
                                                ClothingTags.WindResistance.STORMY,
                                                ClothingTags.BodySection.TORSO_OUTER)));

                return clothes;
        }

        @Test
        void testRecommendationForColdAndSnowyWeather() {
                Weather coldSnowyWeather = new Weather(new BigDecimal("-5"), BigDecimal.valueOf(20), Optional.empty(),
                                Optional.of(BigInteger.ONE));

                Optional<Clothing> recommendedClothing = recommendationCreator.createClothingRecommendation(
                                coldSnowyWeather,
                                createColdWetTorsoWardrobe());
                assertTrue(recommendedClothing.isPresent());
                assertEquals(ClothingTags.BodySection.TORSO_OUTER,
                                recommendedClothing.get().getTags().getBodySection());
                assertEquals(ClothingType.JACKET, recommendedClothing.get().getType());
        }

        private List<Clothing> createColdTorsoWardrobe() {
                List<Clothing> clothes = new ArrayList<>();

                // Suitable for cold, snowy weather
                clothes.add(new Clothing(
                                BigInteger.valueOf(1),
                                ClothingType.JACKET,
                                "Name 1",

                                new ClothingTags(
                                                ClothingTags.Temperature.COLD,
                                                ClothingTags.PrecipitationProtection.SNOWFALL,
                                                ClothingTags.WaterResistance.HIGH,
                                                ClothingTags.WindResistance.STORMY,
                                                ClothingTags.BodySection.TORSO_OUTER)));

                // Another suitable option for cold, but heavy rain
                clothes.add(new Clothing(
                                BigInteger.valueOf(2),
                                ClothingType.TRENCH_COAT,
                                "Name 2",

                                new ClothingTags(
                                                ClothingTags.Temperature.COLD,
                                                ClothingTags.PrecipitationProtection.HEAVY_RAIN,
                                                ClothingTags.WaterResistance.HIGH,
                                                ClothingTags.WindResistance.WINDY,
                                                ClothingTags.BodySection.TORSO_OUTER)));

                return clothes;
        }

        @Test
        void testRecommendationForColdTorso() {
                Weather coldWeather = new Weather(new BigDecimal("-2"), BigDecimal.valueOf(15),
                                Optional.of(BigInteger.ONE),
                                Optional.empty());

                Optional<Clothing> recommendedClothing = recommendationCreator.createClothingRecommendation(coldWeather,
                                createColdTorsoWardrobe());
                assertTrue(recommendedClothing.isPresent());
                assertEquals(ClothingTags.BodySection.TORSO_OUTER,
                                recommendedClothing.get().getTags().getBodySection());
                assertTrue(recommendedClothing.get().getType() == ClothingType.JACKET
                                || recommendedClothing.get().getType() == ClothingType.TRENCH_COAT);
        }

        private List<Clothing> createWarmLegsWardrobe() {
                List<Clothing> clothes = new ArrayList<>();

                // Suitable for warm, clear weather
                clothes.add(new Clothing(
                                BigInteger.valueOf(1),
                                ClothingType.SHORTS,
                                "Name 1",

                                new ClothingTags(
                                                ClothingTags.Temperature.WARM,
                                                ClothingTags.PrecipitationProtection.CLEAR,
                                                ClothingTags.WaterResistance.NONE,
                                                ClothingTags.WindResistance.CALM, ClothingTags.BodySection.LEGS)));

                // Another option for warm but breezy conditions
                clothes.add(new Clothing(
                                BigInteger.valueOf(2),
                                ClothingType.SKIRT,
                                "Name 2",

                                new ClothingTags(
                                                ClothingTags.Temperature.WARM,
                                                ClothingTags.PrecipitationProtection.CLEAR,
                                                ClothingTags.WaterResistance.NONE,
                                                ClothingTags.WindResistance.BREEZE, ClothingTags.BodySection.LEGS)));

                return clothes;
        }

        @Test
        void testRecommendationForWarmLegs() {
                Weather warmWeather = new Weather(new BigDecimal("22"), BigDecimal.valueOf(3), Optional.empty(),
                                Optional.empty());

                Optional<Clothing> recommendedClothing = recommendationCreator.createClothingRecommendation(warmWeather,
                                createWarmLegsWardrobe());
                assertTrue(recommendedClothing.isPresent());
                assertEquals(ClothingTags.BodySection.LEGS, recommendedClothing.get().getTags().getBodySection());
                assertTrue(recommendedClothing.get().getType() == ClothingType.SHORTS
                                || recommendedClothing.get().getType() == ClothingType.SKIRT);
        }

        private List<Clothing> createUnsuitableTorsoWardrobe() {
                List<Clothing> clothes = new ArrayList<>();

                // Adding items that are unsuitable for hot, rainy weather
                clothes.add(new Clothing(
                                BigInteger.valueOf(1),
                                ClothingType.COAT,
                                "Name 1",

                                new ClothingTags(
                                                ClothingTags.Temperature.COLD, // Unsuitable for hot weather
                                                ClothingTags.PrecipitationProtection.HEAVY_RAIN,
                                                ClothingTags.WaterResistance.HIGH,
                                                ClothingTags.WindResistance.WINDY,
                                                ClothingTags.BodySection.TORSO_OUTER)));

                clothes.add(new Clothing(
                                BigInteger.valueOf(2),
                                ClothingType.SWEATER,
                                "Name 2",

                                new ClothingTags(
                                                ClothingTags.Temperature.CHILLY, // Still unsuitable for hot weather
                                                ClothingTags.PrecipitationProtection.CLEAR,
                                                ClothingTags.WaterResistance.NONE,
                                                ClothingTags.WindResistance.CALM,
                                                ClothingTags.BodySection.TORSO_INNER)));

                return clothes;
        }

        @Test
        void testNoSuitableTorsoClothingForHotRainyWeather() {
                Weather hotRainyWeather = new Weather(new BigDecimal("30"), BigDecimal.valueOf(5),
                                Optional.of(BigInteger.valueOf(10)), Optional.empty());

                Optional<Clothing> recommendedClothing = recommendationCreator.createClothingRecommendation(
                                hotRainyWeather,
                                new ArrayList<>());
                assertFalse(recommendedClothing.isPresent());
        }

}
