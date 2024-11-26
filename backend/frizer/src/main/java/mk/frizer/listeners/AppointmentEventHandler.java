package mk.frizer.listeners;

import mk.frizer.model.Appointment;
import mk.frizer.model.events.AppointmentCreatedEvent;
import mk.frizer.service.CustomerService;
import mk.frizer.service.EmployeeService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AppointmentEventHandler {
    private final CustomerService customerService;
    private final EmployeeService employeeService;

    public AppointmentEventHandler(CustomerService customerService, EmployeeService employeeService) {
        this.customerService = customerService;
        this.employeeService = employeeService;
    }

    @EventListener
    public void onAppointmentCreated(AppointmentCreatedEvent event) {
        Appointment appointment = (Appointment) event.getSource();
        customerService.addActiveAppointmentForCustomer(appointment);
        employeeService.addActiveAppointmentForEmployee(appointment);
    }
}
