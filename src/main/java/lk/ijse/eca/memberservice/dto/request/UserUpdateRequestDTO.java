package lk.ijse.eca.memberservice.dto.request;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserUpdateRequestDTO {

    @Pattern(
            regexp = "^[a-zA-Z][a-zA-Z ]*$",
            message = "First name can only contain letters and spaces"
    )
    private String firstName;

    @Pattern(
            regexp = "^[a-zA-Z][a-zA-Z ]*$",
            message = "Last name can only contain letters and spaces"
    )
    private String lastName;

    private String phone;

    @Past(message = "Date of birth must be a past date")
    private LocalDate dateOfBirth;

    private String gender;
    
    private String specialization;
}
