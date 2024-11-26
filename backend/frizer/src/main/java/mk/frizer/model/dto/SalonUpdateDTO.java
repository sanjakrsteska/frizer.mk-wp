package mk.frizer.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class SalonUpdateDTO {
    private String name;
    private String description;
    private String location;
    private String phoneNumber;
    private Double rating;
    private Float latitude;
    private Float longitude;
}
