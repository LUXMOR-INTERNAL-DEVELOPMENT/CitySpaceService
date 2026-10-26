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

        String venue = parts[0].trim();
        String locality = parts[2].trim().replaceAll("\\s*-?\\s*\\d{6}\\s*$", "");

        return venue + ", " + locality;
    }
}

