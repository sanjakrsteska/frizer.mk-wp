package mk.frizer.utilities;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DateTimeRounding {

    public static LocalDateTime roundToNextHour(LocalDateTime dateTime) {
        if (dateTime.getMinute() > 0 || dateTime.getSecond() > 0 || dateTime.getNano() > 0) {
            return dateTime.truncatedTo(ChronoUnit.HOURS).plusHours(1);
        } else {
            return dateTime;
        }
    }
}
