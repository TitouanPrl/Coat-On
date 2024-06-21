package io.coaton.activity.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

/**
 * Class containing the tag values for activities.<br>
 * <b>NB!</b> Any changes to this class and contained {@code enum}s must be reflected in {@code ClothingService}
 */
@Embeddable // NOTE: This annotation makes it so this custom class can be used as a data type
@Data
public class ActivityTags {
    private Locale localeType;
    private Exertion exertionLevel;
    private Category category;
    private Temperature temperature;
    private Wind wind;
    private Integer minPeople;
    private Integer maxPeople;
    private Integer recommendedPeople;

    /** Tag to indicate what type of location the activity is taking place in (exclusive) */
    public enum Locale {
        OUTDOORS,
        INDOORS,
        HYBRID
    }

    /** Tag to indicate the level of physical exertion required by the activity */
    public enum Exertion {
        CALM,
        LOW,
        MODERATE,
        HIGH
    }

    // TODO: Currently these are pretty random
    //  - Might benefit from better categorization
    /** Tag to indicate the broad category of the activity (exclusive) */
    public enum Category {
        SPORTS,
        CONCERT,
        CONFERENCE,
        EXPOSITION,
        FESTIVAL,
        CRAFTS,
        OTHER
    }

    /** Tag to indicate the most suitable temperature for attending the activity ({@code enum} in non-linear scale) */
    public enum Temperature {
        TEMPERATE,
        COLD,
        CHILLY,
        WARM,
        HOT
    }

    /** Tag tp indicate the most suitable wind conditions for attending the activity ({@code enum} in linear scale) */
    public enum Wind {
        CALM,
        BREEZE,
        WINDY,
        GALE,
        STORMY
    }
}
