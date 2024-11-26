package mk.frizer.utilities;

import mk.frizer.model.Salon;

public class SalonAdapter {
        public static String convertToString(Salon salon) {
            return String.format("%d|%s|%s|%s", salon.getId(), salon.getLatitude(), salon.getLongitude(), salon.getName());
            }
}
