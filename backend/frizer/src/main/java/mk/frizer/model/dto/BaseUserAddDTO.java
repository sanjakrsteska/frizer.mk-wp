package mk.frizer.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseUserAddDTO {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
