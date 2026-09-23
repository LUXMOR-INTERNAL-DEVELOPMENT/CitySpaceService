package app.util;

public class LocationUtil {

    public static String getLocality(String fullAddress) {

        if (fullAddress == null || fullAddress.isBlank()) {
            return fullAddress;
        }

        String[] parts = fullAddress.split(",");

        if (parts.length < 3) {
            return fullAddress.trim();
        }

        return parts[2].trim();
    }
}
