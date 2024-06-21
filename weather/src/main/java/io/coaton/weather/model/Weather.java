package io.coaton.weather.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import lombok.*;

@AllArgsConstructor
@Data
@Builder
/**
 * Class used to represent relevant information about the weather.
 * {@code location} - Location object with latitude and longitude.
 * {@code datetime} - Time of the weather in Unix, UTC
 * {@code humidity} - As a percentage, value 0 - 100.
 * {@code uvi} - UV index
 * {@code cloudiness} - As a percentage, value 0 - 100.
 * {@code windSpeed} - Metres per second
 * {@code weatherCategory} - includes the dominant aspect of the weather and its
 * description
 * {@code alert} - info about an alert
 * {@code summary} - Human-readable description of the weather conditions for
 * the day
 * {@code precipitationProbability} - Scale of 0 to 1, 0 = 0% and 1 = 100%
 * {@code rain} - Rain volume in mm, if applicable.
 * {@code snow} - Snow volume in mm, if applicable.
 */
public class Weather {
  private Location location;
  private BigInteger datetime;
  private BigInteger humidity;
  private BigInteger uvi;
  private BigInteger cloudiness;
  private BigDecimal windSpeed;
  private WeatherCategory weatherCategory;
  private Alert alert;
  private String summary;
  private BigDecimal precipitationProbability;
  private BigInteger rain;
  private BigInteger snow;
}
