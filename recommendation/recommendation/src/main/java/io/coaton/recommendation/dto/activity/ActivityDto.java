package io.coaton.recommendation.dto.activity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

import static io.coaton.recommendation.service.ActivityRecommendationService.DATE_TIME_FORMATTER;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityDto {
    @Id
    private BigInteger id;
    @NonNull // NOTE: User is defined in other service's module, can not use. Rely on userId
             // instead
    private BigInteger userId; // Used to determine the owner (has access to modify)
    @NonNull
    private String name;
    private String description;
    @NonNull
    private BigDecimal latitude;
    @NonNull
    private BigDecimal longitude;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDateTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDateTime;
    @Embedded
    private ActivityTags tags;
    @Enumerated(EnumType.STRING)
    private ActivityStatus status; // Used for Kafka events

    public ActivityDto(JSONObject activityJson) {

        this.id = activityJson.getBigInteger("id");

        this.userId = activityJson.getBigInteger("userId");

        this.name = activityJson.getString("name");

        this.description = activityJson.optString("description");

        this.latitude = activityJson.getBigDecimal("latitude");

        this.longitude = activityJson.getBigDecimal("longitude");
        this.startDateTime = LocalDateTime.parse(activityJson.getString("startDateTime"), DATE_TIME_FORMATTER);
        this.endDateTime = LocalDateTime.parse(activityJson.getString("endDateTime"), DATE_TIME_FORMATTER);

        this.tags = new ActivityTags(activityJson.getJSONObject("tags"));

        this.status = ActivityStatus.valueOf(activityJson.optString("status", "CREATED"));
    }
}
