package io.coaton.weather.model;

import java.math.BigInteger;
import java.util.List;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
/**
 * Class used to represent relevant information about the Alert.
 * {@code sender} - Name of the alert source. https://openweathermap.org/api/one-call-3#listsource
 * {@code event} - Alert event name.
 * {@code startTime} - Date and time of the start of the alert, Unix, UTC.
 * {@code endTime} - Date and time of the end of the alert, Unix, UTC
 * {@code description} - Description of the alert.
 * {@code tags} - Type of severe weather.
 */
public class Alert {

  private String sender;
  private String event;
  private BigInteger startTime;
  private BigInteger endTime;
  private String description;
  private List<String> tags;
}
