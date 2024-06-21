package io.coaton.activity;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.coaton.activity.model.Activity;
import io.coaton.activity.model.ActivityTags;
import io.coaton.activity.model.ActivityTags.Locale;
import io.coaton.activity.repository.ActivityRepository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner loadProductsData(ActivityRepository activityRepository) {
        return args -> {
            String description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";
            List<Map<String, Object>> cities = new ArrayList<>();

            cities.add(createCity("Tallinn", 24.7536, 59.437));
            cities.add(createCity("Tartu", 26.715, 58.3776));
            cities.add(createCity("Tokyo", 139.6917, 35.6895));
            cities.add(createCity("Seoul", 126.978, 37.5665));
            cities.add(createCity("Los Angeles", -118.2437, 34.0522));
            cities.add(createCity("London", -0.1276, 51.5074));
            cities.add(createCity("Anadyr", 177.5103, 64.73424));
            cities.add(createCity("Managua", -86.251389, 12.136389));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime startTime = LocalDateTime.parse("2023-11-11 22:12:12", formatter);
            LocalDateTime endTime = LocalDateTime.parse("2024-12-12 23:13:13", formatter);

            String[] activityNames = { "Jogging", "Sleeping", "Singing", "Skateboarding", "Dancing", "Reading",
                    "Scrolling on the phone", "See a movie" };

            for (int i = 0; i < cities.size(); i++) {
                Map<String, Object> city = cities.get(i);
                double longitude = (double) city.get("longitude");
                double latitude = (double) city.get("latitude");

                ActivityTags tags = new ActivityTags();
                tags.setLocaleType(Locale.OUTDOORS);

                Activity activity = new Activity();
                activity.setName(activityNames[i]);
                activity.setDescription(description);
                activity.setLatitude(BigDecimal.valueOf(latitude));
                activity.setLongitude(BigDecimal.valueOf(longitude));
                activity.setUserId(BigInteger.ONE);
                activity.setTags(tags);
                activity.setStartDateTime(startTime);
                activity.setEndDateTime(endTime);

                activityRepository.save(activity);

            }
        };
    }

    private static Map<String, Object> createCity(String name, double longitude, double latitude) {
        Map<String, Object> city = new HashMap<>();
        city.put("name", name);
        city.put("longitude", longitude);
        city.put("latitude", latitude);
        return city;
    }
}
