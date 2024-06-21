package io.coaton.activity.service;

import io.coaton.activity.configuration.KafkaTopicConfiguration;
import io.coaton.activity.dto.ActivityDto;
import io.coaton.activity.model.Activity;
import io.coaton.activity.model.ActivityStatus;
import io.coaton.activity.model.ActivityTags;
import io.coaton.activity.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ActivityService {
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private WebClient.Builder webClientBuilder;

    private final KafkaTemplate<String, ActivityDto> kafkaTemplate;

    public Optional<ActivityDto> getActivity(BigInteger activityId) {
        return activityRepository.findById(activityId).map(this::mapToActivityDto);
    }

    public List<ActivityDto> getAllActivities(Map<String,String> tags) {
        Set<Activity> activities = new HashSet<>();

        if (tags == null || tags.isEmpty()) {
            activityRepository.findAll().forEach(activities::add);
            return activities.stream().map(this::mapToActivityDto).toList();
        }

        Map<String, Double> distanceValues = mapToDistanceValues(tags);
        ActivityTags activityTags = mapToActivityTags(tags);

        if (distanceValues.isEmpty()) {
            activities.addAll(activityRepository.findActivityByTags(
                    activityTags.getLocaleType(),
                    activityTags.getExertionLevel(),
                    activityTags.getCategory(),
                    activityTags.getTemperature(),
                    activityTags.getWind(),
                    activityTags.getMinPeople(),
                    activityTags.getMaxPeople(),
                    activityTags.getRecommendedPeople()
            ));
            return activities.stream().map(this::mapToActivityDto).toList();
        } else if (tags.size() > 3) { // Distance values is always empty or has 3 values, more than 3 means there are activity tags included
            activities.addAll(activityRepository.findActivityByTags(
                    activityTags.getLocaleType(),
                    activityTags.getExertionLevel(),
                    activityTags.getCategory(),
                    activityTags.getTemperature(),
                    activityTags.getWind(),
                    activityTags.getMinPeople(),
                    activityTags.getMaxPeople(),
                    activityTags.getRecommendedPeople()
            ));
            return ActivityServiceHelper.filterDistance(activities, distanceValues)
                    .stream().map(this::mapToActivityDto).toList();
        } else {
            activityRepository.findAll().forEach(activities::add);
            return ActivityServiceHelper.filterDistance(activities, distanceValues)
                    .stream().map(this::mapToActivityDto).toList();
        }
    }

    public void addActivity(ActivityDto activityDto) {
        if ( activityDto.getId() != null && activityRepository.findById(activityDto.getId()).isPresent() )
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Activity with matching ID already exists");

        activityDto.setStatus(ActivityStatus.CREATED);
        activityRepository.save(mapToActivity(activityDto));
        kafkaTemplate.send(KafkaTopicConfiguration.Topics.ACTIVITY, activityDto);
    }

    public void updateActivity(ActivityDto activityDto) {
        if ( activityDto.getId() != null && activityRepository.findById(activityDto.getId()).isEmpty() )
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Activity with matching ID not found");
        activityDto.setStatus(ActivityStatus.MODIFIED);
        activityRepository.save(mapToActivity(activityDto));
        kafkaTemplate.send(KafkaTopicConfiguration.Topics.ACTIVITY, activityDto);
    }

    public void deleteActivity(BigInteger activityId) {
        Optional<Activity> activity = activityRepository.findById(activityId);
        if ( activity.isEmpty() )
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Activity with matching ID not found");
        activity.get().setStatus(ActivityStatus.DELETED);
        activityRepository.deleteById(activityId);
        kafkaTemplate.send(KafkaTopicConfiguration.Topics.ACTIVITY, mapToActivityDto(activity.get()));
    }

    private ActivityDto mapToActivityDto(Activity activity) {
        return ActivityDto.builder()
                .id( activity.getId() )
                .userId( activity.getUserId() )
                .name( activity.getName() )
                .description( activity.getDescription() )
                .latitude( activity.getLatitude() )
                .longitude( activity.getLongitude() )
                .startDateTime( activity.getStartDateTime() )
                .endDateTime( activity.getEndDateTime() )
                .tags( activity.getTags() )
                .status( activity.getStatus() )
                .build();
    }

    private Activity mapToActivity(ActivityDto activityDto) {
        return Activity.builder()
                .id( activityDto.getId() )
                .userId( activityDto.getUserId() )
                .name( activityDto.getName() )
                .description( activityDto.getDescription() )
                .latitude( activityDto.getLatitude() )
                .longitude( activityDto.getLongitude() )
                .startDateTime( activityDto.getStartDateTime() )
                .endDateTime( activityDto.getEndDateTime() )
                .tags( activityDto.getTags() )
                .status( activityDto.getStatus() )
                .build();
    }

    private ActivityTags mapToActivityTags(Map<String,String> rawTags) {
        ActivityTags tags = new ActivityTags();

        for (String key : rawTags.keySet()) {
            try {
                switch (key) {
                    case "localeType" -> tags.setLocaleType(ActivityTags.Locale.valueOf(rawTags.get(key)));
                    case "exertionLevel" -> tags.setExertionLevel(ActivityTags.Exertion.valueOf(rawTags.get(key)));
                    case "category" -> tags.setCategory(ActivityTags.Category.valueOf(rawTags.get(key)));
                    case "temperature" -> tags.setTemperature(ActivityTags.Temperature.valueOf(rawTags.get(key)));
                    case "wind" -> tags.setWind(ActivityTags.Wind.valueOf(rawTags.get(key)));
                    case "minPeople" -> tags.setMinPeople(Integer.parseInt(rawTags.get(key)));
                    case "maxPeople" -> tags.setMaxPeople(Integer.parseInt(rawTags.get(key)));
                    case "recommendedPeople" -> tags.setRecommendedPeople(Integer.parseInt(rawTags.get(key)));
                    default -> throw new IllegalArgumentException("Tag not recognized");
                }
            } catch (IllegalArgumentException ignored) {
                // Tag not recognized
            }
        }

        return tags;
    }

    private HashMap<String, Double> mapToDistanceValues(Map<String,String> rawTags) {
        HashMap<String, Double> values = new HashMap<>();

        for (String key : rawTags.keySet()) {
            try {
                switch (key) {
                    case "lat" -> values.put("lat", Double.parseDouble(rawTags.get(key)));
                    case "long" -> values.put("long", Double.parseDouble(rawTags.get(key)));
                    case "radius" -> values.put("radius", Double.parseDouble(rawTags.get(key)));
                    default -> throw new IllegalArgumentException("Tag not recognized");
                }
            } catch (NumberFormatException nfe) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid format for tag: " + key);
            } catch (IllegalArgumentException ignored) {
                // Tag not recognized, skip
            }
        }

        if (values.size() > 0 && values.size() < 3)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid amount of distance tags, expected 3 but got " + values.size());

        return values;
    }
}
