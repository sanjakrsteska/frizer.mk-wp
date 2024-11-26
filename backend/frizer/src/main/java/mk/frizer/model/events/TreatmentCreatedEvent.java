package mk.frizer.model.events;


import lombok.Getter;
import mk.frizer.model.Salon;
import mk.frizer.model.Treatment;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
public class TreatmentCreatedEvent extends ApplicationEvent {
    private LocalDateTime when;

    public TreatmentCreatedEvent(Treatment source) {
        super(source);
        this.when = LocalDateTime.now();
    }

    public TreatmentCreatedEvent(Treatment source, LocalDateTime when) {
        super(source);
        this.when = when;
    }
}
