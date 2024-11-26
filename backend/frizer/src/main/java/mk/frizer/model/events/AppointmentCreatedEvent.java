package mk.frizer.model.events;

import lombok.Getter;
import mk.frizer.model.Appointment;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
public class AppointmentCreatedEvent extends ApplicationEvent {
    private LocalDateTime when;

    public AppointmentCreatedEvent(Appointment source) {
        super(source);
        this.when = LocalDateTime.now();
    }

    public AppointmentCreatedEvent(Appointment source, LocalDateTime when) {
        super(source);
        this.when = when;
    }
}
