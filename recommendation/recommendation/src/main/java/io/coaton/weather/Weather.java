package io.coaton.weather;

import io.coaton.clothing.ClothingTags;
import io.coaton.clothing.ClothingTags.PrecipitationProtection;
import io.coaton.clothing.ClothingTags.Temperature;
import io.coaton.clothing.ClothingTags.WindResistance;
import io.coaton.recommendation.dto.WeatherDto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

import lombok.*;

@Getter
public class Weather {
    private ClothingTags tags = new ClothingTags();

    public Weather(BigDecimal temperature, BigDecimal wind, Optional<BigInteger> rain, Optional<BigInteger> snow) {
        this.setTemperature(temperature);
        this.setPrecipitation(rain, snow);
        this.setWind(wind);
    }

    public Weather(WeatherDto weatherDto) {
        this(weatherDto.getTemperature(), weatherDto.getWindSpeed(), weatherDto.getRain(), weatherDto.getSnow());
    }

    private void setTemperature(BigDecimal temperature) {
        if (WeatherTag.Constants.HOT_TEMPERATURE.contains(temperature.intValue())) {
            this.tags.setTemperature(Temperature.HOT);
        } else if (WeatherTag.Constants.WARM_TEMPERATURE.contains(temperature.intValue())) {
            this.tags.setTemperature(Temperature.WARM);
        } else if (WeatherTag.Constants.TEMPERATE_TEMPERATURE.contains(temperature.intValue())) {
            this.tags.setTemperature(Temperature.TEMPERATE);
        } else if (WeatherTag.Constants.CHILLY_TEMPERATURE.contains(temperature.intValue())) {
            this.tags.setTemperature(Temperature.CHILLY);
        } else {
            this.tags.setTemperature(Temperature.COLD);
        }
    }

    private void setWind(BigDecimal wind) {
        if (WeatherTag.Constants.STORMY_WIND.contains(wind.intValue())) {
            this.tags.setWindResistance(WindResistance.STORMY);
        } else if (WeatherTag.Constants.GALE_WIND.contains(wind.intValue())) {
            this.tags.setWindResistance(WindResistance.GALE);
        } else if (WeatherTag.Constants.WINDY_WIND.contains(wind.intValue())) {
            this.tags.setWindResistance(WindResistance.WINDY);
        } else if (WeatherTag.Constants.BREEZE_WIND.contains(wind.intValue())) {
            this.tags.setWindResistance(WindResistance.BREEZE);
        } else {
            this.tags.setWindResistance(WindResistance.CALM);
        }
    }

    private void setPrecipitation(Optional<BigInteger> rain, Optional<BigInteger> snow) {
        if (rain.isPresent()) {
            this.tags.setPrecipitationProtection(PrecipitationProtection.HEAVY_RAIN);
        } else if (snow.isPresent()) {
            this.tags.setPrecipitationProtection(PrecipitationProtection.SNOWFALL);
        } else {
            this.tags.setPrecipitationProtection(PrecipitationProtection.CLEAR);
        }

    }

}
