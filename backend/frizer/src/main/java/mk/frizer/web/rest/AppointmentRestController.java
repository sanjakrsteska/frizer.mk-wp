package mk.frizer.web.rest;

import mk.frizer.model.Appointment;
import mk.frizer.model.BusinessOwner;
import mk.frizer.model.dto.AppointmentAddDTO;
import mk.frizer.model.exceptions.AppointmentNotFoundException;
import mk.frizer.model.exceptions.UserNotFoundException;
import mk.frizer.service.AppointmentService;
import mk.frizer.service.BusinessOwnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping({ "/api/appointments", "/api/appointment" })
@CrossOrigin(origins = { "localhost:3000", "localhost:3001" })
public class AppointmentRestController {
    private final AppointmentService appointmentService;

    public AppointmentRestController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping()
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAppointments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
        return this.appointmentService.getAppointmentById(id)
                .map(owner -> ResponseEntity.ok().body(owner))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Appointment> createAppointment(@RequestBody AppointmentAddDTO appointmentAddDTO) {
        return this.appointmentService.createAppointment(appointmentAddDTO)
                .map(appointment -> ResponseEntity.ok().body(appointment))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Appointment> deleteAppointmentById(@PathVariable Long id) {
        return this.appointmentService.deleteAppointmentById(id)
                .map(customer -> ResponseEntity.ok().body(customer))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/attended/{id}")
    public ResponseEntity<Appointment> changeAttendanceForAppointment(@PathVariable Long id) {
        return this.appointmentService.changeUserAttendanceAppointment(id)
                .map(appointment -> ResponseEntity.ok().body(appointment))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

}
