package io.coaton.weather.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.json.JSONObject;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
/**
 * Class used to represent relevant information about daily weather.
 * The main difference with Current Weather are the multiple attributes for
 * temperature and feelsLike.
 * {@code temperature} - Temperature in Celsius.
 * {@code feelsLike} - Temperature in Celsius.
 */
public class DailyWeather extends Weather {
    private Temperature temperatures;
    private Temperature feelsLikeTemperatures;

    public DailyWeather(Location location, BigInteger datetime, BigInteger humidity, BigInteger uvi,
            BigInteger cloudiness, BigDecimal windSpeed, WeatherCategory weatherCategory, Alert alert, String summary,
            BigDecimal precipitationProbability, BigInteger rain, BigInteger snow, JSONObject temperatures,
            JSONObject feelslikeTemperatures) {
        super(location, datetime, humidity, uvi, cloudiness, windSpeed, weatherCategory, alert, summary,
                precipitationProbability, rain, snow);
        this.temperatures = mapToTemperature(temperatures);
        this.feelsLikeTemperatures = mapToTemperature(feelslikeTemperatures);
    }

    public DailyWeather(Weather weather) {
        super(weather.getLocation(), weather.getDatetime(), weather.getHumidity(), weather.getUvi(),
                weather.getCloudiness(), weather.getWindSpeed(), weather.getWeatherCategory(), weather.getAlert(),
                weather.getSummary(),
                weather.getPrecipitationProbability(), weather.getRain(), weather.getSnow());
    }

    private Temperature mapToTemperature(JSONObject temperatures) {
        return Temperature.builder().day(temperatures.getBigDecimal("day")).morning(temperatures.getBigDecimal("morn"))
                .evening(temperatures.getBigDecimal("eve"))
                .night(temperatures.getBigDecimal("night")).min(temperatures.optBigDecimal("min", null))
                .max((temperatures.optBigDecimal("max", null))).build();
    }

    public void setTemperatures(JSONObject jsonObject) {
        this.temperatures = mapToTemperature(jsonObject);
    }

    public void setFeelsLikeTemperatures(JSONObject jsonObject) {
        this.feelsLikeTemperatures = mapToTemperature(jsonObject);
    }
}

@Data
@Builder
class Temperature {
    BigDecimal day;
    BigDecimal evening;
    BigDecimal night;
    BigDecimal morning;
    BigDecimal min;
    BigDecimal max;

}
