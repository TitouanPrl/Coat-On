package io.coaton.clothing;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.coaton.clothing.model.Clothing;
import io.coaton.clothing.model.ClothingTags;
import io.coaton.clothing.model.ClothingType;
import io.coaton.clothing.model.ClothingTags.BodySection;
import io.coaton.clothing.model.ClothingTags.PrecipitationProtection;
import io.coaton.clothing.model.ClothingTags.Temperature;
import io.coaton.clothing.model.ClothingTags.WaterResistance;
import io.coaton.clothing.model.ClothingTags.WindResistance;
import io.coaton.clothing.repository.ClothingRepository;

import java.math.BigInteger;

@Configuration
public class DataInitializer {

        @Bean
        public CommandLineRunner loadProductsData(ClothingRepository clothingRepository) {
                BigInteger userId = BigInteger.valueOf(352);
                return args -> {
                        Clothing clothing1 = new Clothing();
                        clothing1.setName("T shirt");
                        clothing1.setType(ClothingType.SHIRT);
                        clothing1.setUserId(userId);
                        clothing1.setTags(new ClothingTags(Temperature.WARM, PrecipitationProtection.CLEAR,
                                        WaterResistance.NONE,
                                        WindResistance.CALM, BodySection.TORSO_INNER));

                        clothingRepository.save(clothing1);

                        Clothing clothing2 = new Clothing();
                        clothing2.setName("Black jeans");
                        clothing2.setType(ClothingType.PANTS);
                        clothing2.setUserId(userId);
                        clothing2.setTags(
                                        new ClothingTags(Temperature.TEMPERATE, PrecipitationProtection.CLEAR,
                                                        WaterResistance.NONE,
                                                        WindResistance.CALM, BodySection.LEGS));

                        clothingRepository.save(clothing2);

                        Clothing clothing3 = new Clothing();
                        clothing3.setName("Warm jacket");
                        clothing3.setType(ClothingType.JACKET);
                        clothing3.setUserId(userId);
                        clothing3.setTags(new ClothingTags(Temperature.COLD, PrecipitationProtection.CLEAR,
                                        WaterResistance.NONE,
                                        WindResistance.CALM, BodySection.TORSO_OUTER));

                        clothingRepository.save(clothing3);

                        Clothing clothing4 = new Clothing();
                        clothing4.setName("Leather jacket");
                        clothing4.setType(ClothingType.JACKET);
                        clothing4.setUserId(userId);
                        clothing4.setTags(new ClothingTags(Temperature.CHILLY, PrecipitationProtection.CLEAR,
                                        WaterResistance.NONE,
                                        WindResistance.CALM, BodySection.TORSO_OUTER));
                        clothingRepository.save(clothing4);

                        Clothing clothing5 = new Clothing();
                        clothing5.setName("Tank top");
                        clothing5.setType(ClothingType.SHIRT);
                        clothing5.setUserId(userId);
                        clothing5.setTags(new ClothingTags(Temperature.HOT, PrecipitationProtection.CLEAR,
                                        WaterResistance.NONE,
                                        WindResistance.CALM, BodySection.TORSO_OUTER));

                        clothingRepository.save(clothing5);

                        Clothing clothing6 = new Clothing();
                        clothing6.setName("Denim jacket");
                        clothing6.setType(ClothingType.JACKET);
                        clothing6.setUserId(userId);
                        clothing6.setTags(
                                        new ClothingTags(Temperature.TEMPERATE, PrecipitationProtection.CLEAR,
                                                        WaterResistance.NONE,
                                                        WindResistance.CALM, BodySection.TORSO_OUTER));

                        clothingRepository.save(clothing6);

                        Clothing clothing7 = new Clothing();
                        clothing7.setName("Shorts");
                        clothing7.setType(ClothingType.PANTS);
                        clothing7.setUserId(userId);
                        clothing7.setTags(new ClothingTags(Temperature.HOT, PrecipitationProtection.CLEAR,
                                        WaterResistance.NONE,
                                        WindResistance.CALM, BodySection.LEGS));

                        clothingRepository.save(clothing7);

                        Clothing clothing8 = new Clothing();
                        clothing8.setName("Light skirt");
                        clothing8.setType(ClothingType.PANTS);
                        clothing8.setUserId(userId);
                        clothing8.setTags(new ClothingTags(Temperature.WARM, PrecipitationProtection.CLEAR,
                                        WaterResistance.NONE,
                                        WindResistance.CALM, BodySection.LEGS));

                        clothingRepository.save(clothing8);
                };
        }
}