package mk.frizer.model.dto;

import lombok.Data;

@Data
public class TreatmentUpdateDTO {
    private String name;
    private Double price;
    private Integer duration;
}
