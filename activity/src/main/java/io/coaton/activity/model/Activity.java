package io.coaton.activity.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@Table(name = "activity")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private BigInteger id;
    @NonNull // NOTE: User is defined in other service's module, can not use. Rely on userId instead
    private BigInteger userId; // Used to determine the owner (has access to modify)
    @NonNull
    private String name;
    private String description;
    @NonNull
    private BigDecimal latitude;
    @NonNull
    private BigDecimal longitude;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDateTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDateTime;
    @Embedded
    private ActivityTags tags;
    @Enumerated(EnumType.STRING)
    private ActivityStatus status; // Used for Kafka events
}
