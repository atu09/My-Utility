package atirek.pothiwala.utility.helper;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Formats a distance in meters according to the following rules:
 * <ul>
 * <li>Distances over 0.1 miles return distance in miles with a max of one decimal place.</li>
 * <li>Distances under 0.1 miles return distance in feet rounded down to the nearest 10.</li>
 * <li>Distances under 10 feet return the actual value in feet in normal (list) mode.</li>
 * <li>Distances under 10 feet return "now" in real-time (navigation) mode.</li>
 * </ul>
 */
class DistanceFormatter {

    private DistanceFormatter() {

    }

    static String formatKilometers(float distanceInMeters, boolean realTime) {

        if (distanceInMeters >= 100) {
            return formatDistanceInKilometers(distanceInMeters);
        } else if (distanceInMeters > 10) {
            return formatDistanceOverTenMeters(distanceInMeters);
        } else {
            return formatShortMeters(distanceInMeters, realTime);
        }
    }

    private static String formatDistanceOverTenMeters(float distanceInMeters) {
        return String.format(Locale.getDefault(), "%s m", distanceInMeters);
    }

    private static String formatShortMeters(float distanceInMeters, boolean realTime) {
        if (realTime) {
            return "near by";
        } else {
            return formatDistanceOverTenMeters(distanceInMeters);
        }
    }

    private static String formatDistanceInKilometers(float distanceInMeters) {

        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance();
        decimalFormat.applyPattern("#.#");

        String value = decimalFormat.format(distanceInMeters / 1000);
        return String.format(Locale.getDefault(), "%s km", value);
    }
}

