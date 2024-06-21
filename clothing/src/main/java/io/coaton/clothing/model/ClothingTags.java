package io.coaton.clothing.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class containing enumerations for assigning tags to clothes.<br>
 * <b>NB!</b> Any changes to this class and contained {@code enum}s must be reflected in {@code ClothingService}
 */
@Embeddable // NOTE: This annotation makes it so this custom class can be used as a data type
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClothingTags {
    private Temperature temperature;
    private PrecipitationProtection precipitationProtection;
    private WaterResistance waterResistance;
    private WindResistance windResistance;
    private BodySection bodySection;

    /** Tag to indicate the most suitable temperature for wearing the clothing item ({@code enum} in non-linear scale) */
    public enum Temperature {
        TEMPERATE,
        COLD,
        CHILLY,
        WARM,
        HOT
    }

    /** Tag to indicate the most suitable precipitation amount for wearing the clothing item ({@code enum} in non-linear scale) */
    public enum PrecipitationProtection {
        CLEAR,
        LIGHT_RAIN,
        HEAVY_RAIN,
        SNOWFALL,
        HAIL
    }

    /** Tag to indicate the maximum amount of water a clothing item can withhold ({@code enum} in linear scale) */
    public enum WaterResistance {
        NONE,
        SOME,
        NORMAL,
        HIGH,
        HIGHEST
    }

    /** Tag to indicate the maximum wind strength a clothing item can withhold ({@code enum} in linear scale) */
    public enum WindResistance {
        CALM,
        BREEZE,
        WINDY,
        GALE,
        STORMY
    }

    /** Tag to indicate which body section the clothing item is meant to be worn on (exclusive) */
    public enum BodySection {
        HEAD,
        FEET,
        HANDS,
        TORSO_INNER,
        TORSO_OUTER,
        LEGS,
        TORSO_AND_LEGS
    }
}
