package lk.ijse.eca.memberservice.dto.response;

import lk.ijse.eca.memberservice.entity.Role;
import lombok.Data;

@Data
public class UserResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String profileImageUrl;
    private Role role;
}
