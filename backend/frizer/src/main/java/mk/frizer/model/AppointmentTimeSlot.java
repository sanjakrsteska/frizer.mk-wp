package mk.frizer.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
public class AppointmentTimeSlot {
    public LocalDateTime from;
    public LocalDateTime to;

    public AppointmentTimeSlot(LocalDateTime from, LocalDateTime to) {
        if (!isDivisibleBy20Minutes(from) || !isDivisibleBy20Minutes(to)) {
            throw new IllegalArgumentException("Appointment times must be divisible by 20 minutes.");
        }
        this.from = from.truncatedTo(ChronoUnit.MINUTES);
        this.to = to.truncatedTo(ChronoUnit.MINUTES);
    }

    private boolean isDivisibleBy20Minutes(LocalDateTime dateTime) {
        int minutes = dateTime.getMinute();
        return minutes % 20 == 0;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "from=" + from.getHour() + ":" + from.getMinute() +
                "to=" + to.getHour() + ":" + to.getMinute() +
                '}';
    }
}
