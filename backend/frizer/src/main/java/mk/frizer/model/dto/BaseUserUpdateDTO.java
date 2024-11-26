package mk.frizer.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseUserUpdateDTO {
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
