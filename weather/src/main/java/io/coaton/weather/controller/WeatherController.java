package io.coaton.weather.controller;

import io.coaton.weather.model.CurrentWeather;
import io.coaton.weather.model.DailyWeather;
import io.coaton.weather.model.Location;
import io.coaton.weather.service.WeatherService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/weather")
public class WeatherController {

  @Autowired
  private WeatherService weatherService;

  @GetMapping("/current/lat={lat}&lon={lon}")
  @ResponseStatus(HttpStatus.OK)
  public CurrentWeather getCurrentWeather(
      @PathVariable String lat,
      @PathVariable String lon) throws JSONException {
    Location location = Location.create(lat, lon);
    return weatherService.getCurrentWeather(location);

  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public CurrentWeather getWeatherForTime(
          @RequestParam String lat,
          @RequestParam String lon,
          @RequestParam String time) throws JSONException {
    Location location = Location.create(lat, lon);
    return weatherService.getWeatherForTime(location, time);

  }

  @GetMapping("/daily/lat={lat}&lon={lon}")
  @ResponseStatus(HttpStatus.OK)
  public List<DailyWeather> getDailyWeatherForWeek(
      @PathVariable String lat,
      @PathVariable String lon) throws JSONException {
    Location location = Location.create(lat, lon);
    return weatherService.getDailyWeatherForecast(location);
  }

}
