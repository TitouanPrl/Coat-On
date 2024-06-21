package io.coaton.recommendation.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

import io.coaton.recommendation.model.Location;
import lombok.*;
import org.json.JSONObject;

/**
 * Class used to represent relevant information about the weather. <br>
 * {@code location} - Location object with latitude and longitude. <br>
 * {@code datetime} - Time of the weather in Unix, UTC <br>
 * {@code humidity} - As a percentage, value 0 - 100. <br>
 * {@code uvi} - UV index <br>
 * {@code cloudiness} - As a percentage, value 0 - 100. <br>
 * {@code windSpeed} - Metres per second <br>
 * {@code weatherCategory} - includes the dominant aspect of the weather and its
 * description <br>
 * {@code alert} - info about an alert <br>
 * {@code summary} - Human-readable description of the weather conditions for
 * the day <br>
 * {@code precipitationProbability} - Scale of 0 to 1, 0 = 0% and 1 = 100% <br>
 * {@code rain} - Rain volume in mm, if applicable. <br>
 * {@code snow} - Snow volume in mm, if applicable. <br>
 */
@AllArgsConstructor
@Data
@Builder
public class WeatherDto {
  private Location location;
  private BigInteger datetime;
  private BigInteger humidity;
  private BigInteger uvi;
  private BigInteger cloudiness;
  private BigDecimal windSpeed;
  private String summary;
  private BigDecimal precipitationProbability;
  private Optional<BigInteger> rain;
  private Optional<BigInteger> snow;
  private BigDecimal temperature;

  public WeatherDto(String weatherRawJson) {
    JSONObject weather = new JSONObject(weatherRawJson);

    this.location = new Location(
        weather.getJSONObject("location").getString("lat"),
        weather.getJSONObject("location").getString("lon"));
    this.datetime = weather.getBigInteger("datetime");
    this.humidity = weather.getBigInteger("humidity");
    this.uvi = weather.getBigInteger("uvi");
    this.cloudiness = weather.getBigInteger("cloudiness");
    this.windSpeed = weather.getBigDecimal("windSpeed");
    this.summary = weather.getString("summary");
    this.precipitationProbability = weather.optBigDecimal("precipitationProbability", null);
    this.rain = Optional.ofNullable(weather.optBigInteger("rain", null));
    this.snow = Optional.ofNullable(weather.optBigInteger("snow", null));
    this.temperature = weather.getBigDecimal("temperature");
  }
}