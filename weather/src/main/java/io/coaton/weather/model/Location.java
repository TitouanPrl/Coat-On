package io.coaton.weather.model;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
/**
 * Class used to represent the Location.
 * {@code lat} - latitude
 * {@code lon} - longitude
 */
public class Location {

  private String lat;
  private String lon;

  @Override
  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof Location)) return false;

    Location location = (Location) o;

    // TODO Consider adding an acceptable difference between the lon-s and lat-s for two locations to still be considered the same.
    return location.getLon() == this.lon && location.getLat() == this.lat;
  }

  public static Location create(String lat, String lon) {
    return Location.builder().lat(lat).lon(lon).build();
  }
}
