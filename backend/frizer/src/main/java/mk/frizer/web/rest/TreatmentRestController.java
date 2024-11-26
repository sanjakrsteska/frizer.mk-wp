package mk.frizer.web.rest;

import mk.frizer.model.BaseUser;
import mk.frizer.model.Salon;
import mk.frizer.model.Treatment;
import mk.frizer.model.dto.SalonAddDTO;
import mk.frizer.model.dto.SalonUpdateDTO;
import mk.frizer.model.dto.TreatmentAddDTO;
import mk.frizer.model.dto.TreatmentUpdateDTO;
import mk.frizer.model.exceptions.TreatmentNotFoundException;
import mk.frizer.model.exceptions.UserNotFoundException;
import mk.frizer.service.TreatmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping({"/api/treatments", "/api/treatment"})
@CrossOrigin(origins = {"localhost:3000","localhost:3001"})
public class TreatmentRestController {
    private final TreatmentService treatmentService;

    public TreatmentRestController(TreatmentService treatmentService) {
        this.treatmentService = treatmentService;
    }

    @GetMapping()
    public List<Treatment> getTreatments(){
        return treatmentService.getTreatments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Treatment> getTreatment(@PathVariable Long id){
        return this.treatmentService.getTreatmentById(id)
                .map(treatment -> ResponseEntity.ok().body(treatment))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Treatment> createTreatment(@RequestBody TreatmentAddDTO treatmentAddDTO) {
        System.out.println(treatmentAddDTO);
        return this.treatmentService.createTreatment(treatmentAddDTO)
                .map(treatment -> ResponseEntity.ok().body(treatment))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<Treatment> updateTreatment(@PathVariable Long id, @RequestBody TreatmentUpdateDTO treatmentUpdateDTO) {
        return this.treatmentService.updateTreatment(id, treatmentUpdateDTO)
                .map(treatment -> ResponseEntity.ok().body(treatment))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Treatment> deleteTreatmentById(@PathVariable Long id) {
        Optional<Treatment> treatment = this.treatmentService.deleteTreatmentById(id);
        try{
            this.treatmentService.getTreatmentById(id);
            return ResponseEntity.badRequest().build();
        }
        catch(TreatmentNotFoundException exception){
            return ResponseEntity.ok().body(treatment.get());
        }
    }
}
