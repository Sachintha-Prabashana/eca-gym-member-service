package lk.ijse.eca.memberservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainerCreateRequestDTO {

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
    @Size(min = 8, message = "Password must contain at least 8 characters")
    private String password;

    @NotBlank(message = "Phone is required")
    private String phone;

    @NotBlank(message = "Specialization is required")
    private String specialization;
}