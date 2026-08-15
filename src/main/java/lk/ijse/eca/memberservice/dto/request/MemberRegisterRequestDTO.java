package lk.ijse.eca.memberservice.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MemberRegisterRequestDTO {
    @NotBlank(message = "First name is required")
    @Pattern(
            regexp = "^[a-zA-Z][a-zA-Z ]*$",
            message = "First name can only contain letters and spaces"
    )
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Pattern(
            regexp = "^[a-zA-Z][a-zA-Z ]*$",
            message = "Last name can only contain letters and spaces"
    )
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(
            min = 8,
            message = "Password must contain at least 8 characters"
    )
    private String password;

    private String phone;

    @Past(message = "Date of birth must be a past date")
    private LocalDate dateOfBirth;

    private String gender;
}
