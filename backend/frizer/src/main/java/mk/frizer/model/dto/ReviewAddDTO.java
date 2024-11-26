package mk.frizer.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewAddDTO {
    private Long employeeId;
    private Long customerId;
    private Double rating;
    private String comment;
}
