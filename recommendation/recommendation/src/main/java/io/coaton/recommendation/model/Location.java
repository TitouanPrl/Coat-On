package io.coaton.recommendation.model;

import lombok.*;

/**
 * Class used to represent the Location.
 * {@code lat} - latitude
 * {@code lon} - longitude
 */
@Builder
@Data
@AllArgsConstructor
public class Location {

    private String lat;
    private String lon;

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Location))
            return false;

        Location location = (Location) o;

        // TODO Consider adding an acceptable difference between the lon-s and lat-s for
        // two locations to still be considered the same.
        return location.getLon().equals(this.lon) && location.getLat().equals(this.lat);
    }

    public static Location create(String lat, String lon) {
        return Location.builder().lat(lat).lon(lon).build();
    }
}
