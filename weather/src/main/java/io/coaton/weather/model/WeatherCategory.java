package io.coaton.weather.model;

import lombok.*;
import org.json.JSONException;
import org.json.JSONObject;

@Builder
@Data
@AllArgsConstructor
/**
 * Class used to represent the weather category.
 * {@code id} - Weather condition ID. https://openweathermap.org/weather-conditions#Weather-Condition-Codes-2
 * {@code category} - Group of weather parameters (Rain, Snow etc.)
 * {@code description} - Weather condition within the group.
 */
public class WeatherCategory {

  private Integer id;
  private String category;
  private String description;

  public static WeatherCategory buildWeatherCategory(JSONObject jsonObject)
    throws JSONException {
    return WeatherCategory
      .builder()
      .category(jsonObject.getString("main"))
      .id(jsonObject.getInt("id"))
      .description(jsonObject.getString("description"))
      .build();
  }
}
