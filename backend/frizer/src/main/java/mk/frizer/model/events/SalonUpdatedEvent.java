package mk.frizer.model.events;

import lombok.Getter;
import mk.frizer.model.Salon;
import mk.frizer.model.Treatment;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
public class SalonUpdatedEvent extends ApplicationEvent {
    private LocalDateTime when;

    public SalonUpdatedEvent(Salon source) {
        super(source);
        this.when = LocalDateTime.now();
    }

    public SalonUpdatedEvent(Salon source, LocalDateTime when) {
        super(source);
        this.when = when;
    }
}
