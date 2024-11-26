package mk.frizer.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TagAddDTO {
    private Long tagId;
    private Long salonId;
}
