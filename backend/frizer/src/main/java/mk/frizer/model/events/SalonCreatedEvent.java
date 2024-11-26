package mk.frizer.model.events;

import lombok.Getter;
import mk.frizer.model.Salon;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
public class SalonCreatedEvent extends ApplicationEvent {
    private LocalDateTime when;

    public SalonCreatedEvent(Salon source) {
        super(source);
        this.when = LocalDateTime.now();
    }

    public SalonCreatedEvent(Salon source, LocalDateTime when) {
        super(source);
        this.when = when;
    }
}

