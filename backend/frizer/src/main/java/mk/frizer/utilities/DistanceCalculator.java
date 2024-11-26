package mk.frizer.utilities;

import org.springframework.stereotype.Component;

@Component
public class DistanceCalculator {
    public DistanceCalculator() {}

    private static final double EARTH_RADIUS_KM = 6371;

    public double getDistance(String userLocation, double wineryLat, double wineryLon) {
        if (userLocation == null) return 0;

        double userLat = Double.parseDouble(userLocation.split(",")[0]);
        double userLon = Double.parseDouble(userLocation.split(",")[1]);

        double dLat = Math.toRadians(wineryLat - userLat);
        double dLon = Math.toRadians(wineryLon - userLon);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(wineryLat)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Distance in kilometers
        return EARTH_RADIUS_KM * c;
    }
}