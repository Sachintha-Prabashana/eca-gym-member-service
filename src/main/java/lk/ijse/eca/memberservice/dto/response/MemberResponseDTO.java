package lk.ijse.eca.memberservice.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MemberResponseDTO extends UserResponseDTO {
    private String phone;
    private LocalDate dob;
    private String gender;
}
