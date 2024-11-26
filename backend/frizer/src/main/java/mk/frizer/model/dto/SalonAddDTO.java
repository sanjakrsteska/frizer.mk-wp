package mk.frizer.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SalonAddDTO {
    private String name;
    private String description;
    private String location;
    private String city;
    private String phoneNumber;
    private Long businessOwnerId;
    private Float latitude;
    private Float longitude;
}
