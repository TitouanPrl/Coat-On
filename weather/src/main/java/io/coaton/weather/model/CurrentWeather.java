package io.coaton.weather.model;

import lombok.*;
import java.math.BigDecimal;
import java.math.BigInteger;

@Data
/**
 * Class used to represent relevant information about the weather of the current
 * moment.
 * The main difference with Daily Weather is the singular attribute for
 * temperature and feelsLike.
 * {@code temperature} - Current temperature in Celsius.
 * {@code feelsLike} - Current feelsLike temperature in Celsius.
 */
public class CurrentWeather extends Weather {
    private BigDecimal temperature;
    private BigDecimal feelsLike;

    public CurrentWeather(Location location, BigInteger datetime, BigInteger humidity, BigInteger uvi,
            BigInteger cloudiness, BigDecimal windSpeed, WeatherCategory weatherCategory, Alert alert, String summary,
            BigDecimal precipitationProbability, BigInteger rain, BigInteger snow, BigDecimal temperature,
            BigDecimal feelsLike) {
        super(location, datetime, humidity, uvi, cloudiness, windSpeed, weatherCategory, alert, summary,
                precipitationProbability, rain, snow);

        this.temperature = temperature;
        this.feelsLike = feelsLike;
    }

    public CurrentWeather(Weather weather) {
        super(weather.getLocation(), weather.getDatetime(), weather.getHumidity(), weather.getUvi(),
                weather.getCloudiness(), weather.getWindSpeed(), weather.getWeatherCategory(), weather.getAlert(),
                weather.getSummary(),
                weather.getPrecipitationProbability(), weather.getRain(), weather.getSnow());
    }

}
