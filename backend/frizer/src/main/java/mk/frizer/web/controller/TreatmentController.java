package mk.frizer.web.controller;

import mk.frizer.model.Employee;
import mk.frizer.model.Treatment;
import mk.frizer.model.dto.EmployeeAddDTO;
import mk.frizer.model.dto.TreatmentAddDTO;
import mk.frizer.service.EmployeeService;
import mk.frizer.service.TreatmentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/treatments")
public class TreatmentController {
    private final TreatmentService treatmentService;

    public TreatmentController(TreatmentService treatmentService) {
        this.treatmentService = treatmentService;
    }

    @PostMapping("/add")
    public String addTreatment(@RequestParam Long salonId, @RequestParam String name,
                               @RequestParam Double price, @RequestParam Integer durationMultiplier) {
        Optional<Treatment> treatment = treatmentService.createTreatment(new TreatmentAddDTO(name, salonId, price, durationMultiplier));
        return "redirect:/salons/" + salonId;
    }

    @PostMapping("/remove")
    public String removeTreatmentFromSalon(@RequestParam Long salonId, @RequestParam Long treatmentId) {
        treatmentService.deleteTreatmentByIdFromSalon(treatmentId, salonId);
        return "redirect:/salons/" + salonId;
    }

}

