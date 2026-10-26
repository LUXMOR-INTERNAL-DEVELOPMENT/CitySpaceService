package app.util;

public class DistanceCalculator {

    private static final double EARTH_RADIUS_KM = 6371.0;

    public static double calculateDistance(
            double userLatitude,
            double userLongitude,
            double eventLatitude,
            double eventLongitude) {

        double latDistance = Math.toRadians(eventLatitude - userLatitude);
        double lonDistance = Math.toRadians(eventLongitude - userLongitude);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(userLatitude))
                * Math.cos(Math.toRadians(eventLatitude))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c;
    }
}
