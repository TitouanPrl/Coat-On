package io.coaton.weather;

import jakarta.persistence.Embeddable;
import lombok.Data;
import org.springframework.data.domain.Range;

@Embeddable // NOTE: This annotation makes it so this custom class can be used as a data
            // type
@Data
public class WeatherTag {

    public static class Constants {
        private static final Range.Bound<Integer> HOT_LOWER_BOUND = Range.Bound.inclusive(30);
        private static final Range.Bound<Integer> WARM_HIGHER_BOUND = Range.Bound.exclusive(30);
        private static final Range.Bound<Integer> WARM_LOWER_BOUND = Range.Bound.inclusive(20);
        private static final Range.Bound<Integer> TEMPERATE_HIGHER_BOUND = Range.Bound.exclusive(20);
        private static final Range.Bound<Integer> TEMPERATE_LOWER_BOUND = Range.Bound.inclusive(10);
        private static final Range.Bound<Integer> CHILLY_HIGHER_BOUND = Range.Bound.exclusive(10);
        private static final Range.Bound<Integer> CHILLY_LOWER_BOUND = Range.Bound.inclusive(0);
        private static final Range.Bound<Integer> COLD_HIGHER_BOUND = Range.Bound.exclusive(0);

        public static final Range<Integer> HOT_TEMPERATURE = Range.of(HOT_LOWER_BOUND, Range.Bound.unbounded());
        public static final Range<Integer> WARM_TEMPERATURE = Range.of(WARM_LOWER_BOUND, WARM_HIGHER_BOUND);
        public static final Range<Integer> TEMPERATE_TEMPERATURE = Range.of(
                TEMPERATE_LOWER_BOUND, TEMPERATE_HIGHER_BOUND);
        public static final Range<Integer> CHILLY_TEMPERATURE = Range.of(CHILLY_LOWER_BOUND, CHILLY_HIGHER_BOUND);
        public static final Range<Integer> COLD_TEMPERATURE = Range.of(Range.Bound.unbounded(), COLD_HIGHER_BOUND);

        private static final Range.Bound<Integer> STORMY_LOWER_BOUND = Range.Bound.inclusive(24);
        private static final Range.Bound<Integer> GALE_HIGHER_BOUND = Range.Bound.exclusive(24);
        private static final Range.Bound<Integer> GALE_LOWER_BOUND = Range.Bound.inclusive(17);
        private static final Range.Bound<Integer> WINDY_HIGHER_BOUND = Range.Bound.exclusive(17);
        private static final Range.Bound<Integer> WINDY_LOWER_BOUND = Range.Bound.inclusive(8);
        private static final Range.Bound<Integer> BREEZE_HIGHER_BOUND = Range.Bound.exclusive(8);
        private static final Range.Bound<Integer> BREEZE_LOWER_BOUND = Range.Bound.inclusive(2);
        private static final Range.Bound<Integer> CALM_HIGHER_BOUND = Range.Bound.exclusive(2);

        public static final Range<Integer> CALM_WIND = Range.of(Range.Bound.unbounded(), CALM_HIGHER_BOUND);
        public static final Range<Integer> BREEZE_WIND = Range.of(BREEZE_LOWER_BOUND, BREEZE_HIGHER_BOUND);
        public static final Range<Integer> WINDY_WIND = Range.of(WINDY_LOWER_BOUND, WINDY_HIGHER_BOUND);
        public static final Range<Integer> GALE_WIND = Range.of(GALE_LOWER_BOUND, GALE_HIGHER_BOUND);
        public static final Range<Integer> STORMY_WIND = Range.of(STORMY_LOWER_BOUND, Range.Bound.unbounded());

    }
}
