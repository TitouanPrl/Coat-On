package io.coaton.weather.service;

import io.coaton.weather.model.CurrentWeather;
import io.coaton.weather.model.DailyWeather;
import io.coaton.weather.model.Location;
import io.coaton.weather.model.Weather;
import io.coaton.weather.model.WeatherCategory;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WeatherService {

        @Value("${apiKey}")
        private String apiKey;

        @Autowired
        private WebClient.Builder webClientBuilder;

        public final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        public CurrentWeather getCurrentWeather(Location location) {
                String uriTemplate = "https://api.openweathermap.org/data/3.0/onecall?lat={lat}&lon={lon}&units=metric&appid={apiKey}";

                String res = webClientBuilder.build()
                                .get()
                                .uri(uriTemplate, location.getLat(), location.getLon(), apiKey)
                                .retrieve()
                                .bodyToMono(String.class)
                                .block();

                JSONObject jsonObject = new JSONObject(res).getJSONObject("current");
                return mapToCurrentWeather(jsonObject, mapToWeather(jsonObject, location));

        }

        public List<DailyWeather> getDailyWeatherForecast(Location location) {
                String res = webClientBuilder
                                .build()
                                .get()
                                .uri(
                                                "https://api.openweathermap.org/data/3.0/onecall?lat={lat}&lon={lon}&units=metric&appid={apiKey}",
                                                location.getLat(),
                                                location.getLon(),
                                                apiKey)
                                .retrieve()
                                .bodyToMono(String.class)
                                .block();

                JSONArray jsonArray = new JSONObject(res).getJSONArray("daily");
                List<DailyWeather> dailyWeathers = new ArrayList<>();
                jsonArray.forEach((jsonObject -> dailyWeathers
                                .add(mapToDailyWeather((JSONObject) jsonObject,
                                                mapToWeather((JSONObject) jsonObject, location)))));
                return dailyWeathers;
        }

        public CurrentWeather getWeatherForTime(Location location, String datetimeString) {
                String uriTemplate = "https://api.openweathermap.org/data/3.0/onecall?lat={lat}&lon={lon}&dt={unixTimestamp}&units=metric&appid={apiKey}";
                LocalDateTime datetime = LocalDateTime.parse(datetimeString, DATE_TIME_FORMATTER);
                ZoneOffset zoneOffset = ZoneId.systemDefault().getRules().getOffset(datetime);
                long unixTs = datetime.toEpochSecond(zoneOffset);

                String res = webClientBuilder.build()
                        .get()
                        .uri(uriTemplate, location.getLat(), location.getLon(), unixTs, apiKey)
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();

                JSONObject jsonObject = new JSONObject(res).getJSONObject("current");
                return mapToCurrentWeather(jsonObject, mapToWeather(jsonObject, location));
        }

        private Weather mapToWeather(JSONObject jsonObject, Location location)
                        throws JSONException {
                return Weather
                                .builder()
                                .cloudiness(BigInteger.valueOf(jsonObject.getInt("clouds")))
                                .uvi(BigInteger.valueOf(jsonObject.getInt("uvi")))
                                .humidity(BigInteger.valueOf(jsonObject.getInt("humidity")))
                                .windSpeed(BigDecimal.valueOf(jsonObject.getDouble("wind_speed")))
                                .datetime(BigInteger.valueOf(jsonObject.getInt("dt")))
                                .weatherCategory(
                                                WeatherCategory.buildWeatherCategory(
                                                                jsonObject.getJSONArray("weather").getJSONObject(0)))
                                .rain(jsonObject.optBigInteger("rain", null))
                                .snow(jsonObject.optBigInteger("snow", null))
                                .location(location)
                                .precipitationProbability(jsonObject.optBigDecimal("pop", null))
                                .summary(jsonObject.optString("summary"))
                                .build();
        }

        private CurrentWeather mapToCurrentWeather(JSONObject jsonObject, Weather weather) {
                CurrentWeather currentWeather = new CurrentWeather(weather);
                currentWeather.setTemperature(BigDecimal.valueOf(jsonObject.getDouble("temp")));
                currentWeather.setFeelsLike(BigDecimal.valueOf(jsonObject.getDouble("feels_like")));
                return currentWeather;
        }

        private DailyWeather mapToDailyWeather(JSONObject jsonObject, Weather weather) {
                DailyWeather dailyWeather = new DailyWeather(weather);
                dailyWeather.setTemperatures(jsonObject.getJSONObject("temp"));
                dailyWeather.setFeelsLikeTemperatures(jsonObject.getJSONObject("feels_like"));
                return dailyWeather;
        }
}
