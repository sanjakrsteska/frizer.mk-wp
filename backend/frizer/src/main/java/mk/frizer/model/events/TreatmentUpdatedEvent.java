package mk.frizer.model.events;

import lombok.Getter;
import mk.frizer.model.Treatment;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
public class TreatmentUpdatedEvent extends ApplicationEvent {
    private LocalDateTime when;

    public TreatmentUpdatedEvent(Treatment source) {
        super(source);
        this.when = LocalDateTime.now();
    }

    public TreatmentUpdatedEvent(Treatment source, LocalDateTime when) {
        super(source);
        this.when = when;
    }
}
