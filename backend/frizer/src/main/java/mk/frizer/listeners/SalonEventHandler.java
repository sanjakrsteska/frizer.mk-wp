package mk.frizer.listeners;

import mk.frizer.model.BusinessOwner;
import mk.frizer.model.Salon;
import mk.frizer.model.events.SalonCreatedEvent;
import mk.frizer.model.exceptions.UserNotFoundException;
import mk.frizer.service.BusinessOwnerService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class SalonEventHandler {
    private final BusinessOwnerService businessOwnerService;

    public SalonEventHandler(BusinessOwnerService businessOwnerService) {
        this.businessOwnerService = businessOwnerService;
    }

    @EventListener
    public void onSalonCreated(SalonCreatedEvent event) {
        Salon salon = (Salon)event.getSource();
        businessOwnerService.addSalonToBusinessOwner(salon.getOwner().getId(), salon);
    }
    @EventListener
    public void onSalonUpdated(SalonCreatedEvent event) {
        Salon salon = (Salon)event.getSource();
        businessOwnerService.editSalonForBusinessOwner(salon);
    }


}
