package mk.frizer.jobs;

import mk.frizer.service.AppointmentHistoryService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {
    private final AppointmentHistoryService appointmentHistoryService;

    public ScheduledTask(AppointmentHistoryService appointmentHistoryService) {
        this.appointmentHistoryService = appointmentHistoryService;
    }

//    @Scheduled(fixedDelay = 86400000)
    @Scheduled(cron = "0 0 0 * * *")
    public void addAppointmentsToHistory(){
        appointmentHistoryService.addAppointmentsToHistory();
    }
}
