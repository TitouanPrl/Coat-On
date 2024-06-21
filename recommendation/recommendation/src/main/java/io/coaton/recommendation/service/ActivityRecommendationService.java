package io.coaton.recommendation.service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import org.json.JSONArray;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import io.coaton.recommendation.dto.WeatherDto;
import io.coaton.recommendation.dto.activity.ActivityDto;
import io.coaton.weather.WeatherTag;

@Service
public class ActivityRecommendationService {
    public final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static Map<String, Double> parseLocationValues(String latitude, String longitude, String range) {
        try {
            HashMap<String, Double> values = new HashMap<>();
            if (latitude != null)
                values.put("lat", Double.parseDouble(latitude));
            if (longitude != null)
                values.put("long", Double.parseDouble(longitude));
            if (range != null)
                values.put("radius", Double.parseDouble(range));
            if (values.size() == 3 && values.get("radius") > 0)
                return values;
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Invalid location parameters: lat, long, radius");
        } catch (NumberFormatException nfe) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Failed to parse numeric value: " + nfe.getMessage());
        }
    }

    public static Map<String, LocalDateTime> parseTimeValues(String startDateTime, String endDateTime) {
        try {
            LocalDateTime current = LocalDateTime.now();
            HashMap<String, LocalDateTime> values = new HashMap<>();
            if (startDateTime != null)
                values.put("start", LocalDateTime.parse(startDateTime, DATE_TIME_FORMATTER));
            if (endDateTime != null)
                values.put("end", LocalDateTime.parse(endDateTime, DATE_TIME_FORMATTER));
            if (values.size() == 2 && values.get("start").isAfter(current) && values.get("end").isAfter(current))
                return values;
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Invalid time parameters: start, end");
        } catch (DateTimeParseException dtpe) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to parse datetime: "
                    + dtpe.getParsedString() + "; Use format: " + DATE_TIME_FORMATTER.toString());
        }
    }

    public static List<ActivityDto> parseActivities(String activitiesRawJson) {
        List<ActivityDto> activities = new ArrayList<>();
        JSONArray activitiesJson = new JSONArray(activitiesRawJson);

        for (int i = 0; i < activitiesJson.length(); i++) {
            activities.add(new ActivityDto(activitiesJson.getJSONObject(i)));
        }

        return activities;
    }

    public static Optional<ActivityDto> recommendActivity(Map<String, Double> locationData,
            Map<String, LocalDateTime> timeData,
            WeatherDto weather,
            List<ActivityDto> activities) {
        // Filter activities based on time
        activities = activities.stream()
                .filter(activityDto -> activityDto.getStartDateTime().isBefore(timeData.get("end"))
                        && activityDto.getEndDateTime().isAfter(timeData.get("start")))
                .toList();

        if (activities.isEmpty())
            return Optional.empty(); // No matching activities left

        // Score activities based on tags
        HashMap<BigInteger, Long> scores = new HashMap<>();
        for (ActivityDto activity : activities) {
            long score = 0;
            score = scoreActivityByLocale(score, activity, weather);
            score = scoreActivityByExertion(score, activity, weather);
            score = scoreActivityByTemperature(score, activity, weather);
            score = scoreActivityByWind(score, activity, weather);
            scores.put(activity.getId(), score);
        }

        BigInteger bestId = Collections.max(scores.entrySet(), Map.Entry.comparingByValue()).getKey();
        for (ActivityDto activity : activities) {
            if (activity.getId().equals(bestId))
                return Optional.of(activity);
        }

        return Optional.empty();
    }

    private static long scoreActivityByLocale(long score, ActivityDto activity, WeatherDto weather) {
        if (weather.getPrecipitationProbability() == null || activity.getTags().getLocaleType() == null)
            return score;

        // INDOORS is preferred with precipitation
        // OUTDOORS is preferred with no precipitation
        // HYBRID is preferred in-between
        return switch (activity.getTags().getLocaleType()) {
            case INDOORS -> score + (100 * weather.getPrecipitationProbability().longValue() - 30);
            case OUTDOORS -> score + (30 - 100 * weather.getPrecipitationProbability().longValue());
            case HYBRID -> score + (50 - 100 * weather.getPrecipitationProbability().longValue());
        };
    }

    private static long scoreActivityByExertion(long score, ActivityDto activity, WeatherDto weather) {
        if (weather.getHumidity() == null || activity.getTags().getExertionLevel() == null)
            return score;

        // CALM is preferred with highest humidity
        // LOW is preferred with high humidity
        // MODERATE is preferred with low humidity
        // HIGH is preferred with lowest humidity;
        return switch (activity.getTags().getExertionLevel()) {
            case CALM -> score - (weather.getHumidity().longValue() - 80);
            case LOW -> score - (weather.getHumidity().longValue() - 60);
            case MODERATE -> score - (weather.getHumidity().longValue() - 40);
            case HIGH -> score - (weather.getHumidity().longValue() - 20);
        };
    }

    private static long scoreActivityByTemperature(long score, ActivityDto activity, WeatherDto weather) {
        if (weather.getTemperature() == null || activity.getTags().getTemperature() == null)
            return score;

        int constant = WeatherTag.Constants.HOT_TEMPERATURE.getLowerBound().getValue().isPresent()
                ? WeatherTag.Constants.HOT_TEMPERATURE.getLowerBound().getValue().get()
                : 30; // Default to 15

        // Expected temperature range is 0 - 30, where 0 is end of COLD and 30 is start
        // of HOT
        return switch (activity.getTags().getTemperature()) {
            case COLD -> score + ((constant - 5) - weather.getTemperature().longValue());
            case CHILLY -> score + ((constant - 10) - weather.getTemperature().longValue());
            case TEMPERATE -> score + ((constant - 15) - weather.getTemperature().longValue());
            case WARM -> score + ((constant - 20) - weather.getTemperature().longValue());
            case HOT -> score + ((constant - 25) - weather.getTemperature().longValue());
        };
    }

    private static long scoreActivityByWind(long score, ActivityDto activity, WeatherDto weather) {
        if (weather.getWindSpeed() == null || activity.getTags().getWind() == null)
            return score;

        return switch (activity.getTags().getWind()) {
            case CALM -> score - (weather.getWindSpeed().longValue() - 30);
            case BREEZE -> score - (weather.getWindSpeed().longValue() - 20);
            case WINDY -> score - (15 - weather.getWindSpeed().longValue());
            case GALE -> score - (20 - weather.getWindSpeed().longValue());
            case STORMY -> score - (30 - weather.getWindSpeed().longValue());
        };
    }
}
