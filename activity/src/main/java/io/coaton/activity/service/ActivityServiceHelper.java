package io.coaton.activity.service;

import io.coaton.activity.model.Activity;

import java.util.Map;
import java.util.Set;

public class ActivityServiceHelper {
    public static double EARTH_RADIUS_KM = 6371;

    public static double haversine(double val) {
        return Math.pow(Math.sin(val/2), 2);
    }

    /**
     * Method that calculates the distance between two points in kilometers.<br>
     * Formula and code from: https://www.baeldung.com/java-find-distance-between-points
     * @param startLat Latitude of starting point
     * @param startLong Longitude of starting point
     * @param endLat Latitude of ending point
     * @param endLong Longitude of ending point
     * @return Distance between points in kilometers
     */
    public static double calculateDistanceKm(double startLat, double startLong, double endLat, double endLong) {

        double dLat = Math.toRadians((endLat - startLat));
        double dLong = Math.toRadians((endLong - startLong));

        startLat = Math.toRadians(startLat);
        endLat = Math.toRadians(endLat);

        double a = haversine(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversine(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c;
    }

    /**
     * Method that takes a collection of activities and a collection of distance values (coordinates & radius limit)
     * and filters out all activities with are outside of the given range
     * @param activities The activities to be filtered
     * @param distanceValues The values used for filtering
     * @return The filtered activities
     */
    public static Set<Activity> filterDistance(Set<Activity> activities, Map<String, Double> distanceValues) {
        activities.removeIf(activity -> distanceValues.get("radius") < calculateDistanceKm(
                activity.getLatitude().doubleValue(),
                activity.getLongitude().doubleValue(),
                distanceValues.get("lat"),
                distanceValues.get("long")));

        return activities;
    }
}
