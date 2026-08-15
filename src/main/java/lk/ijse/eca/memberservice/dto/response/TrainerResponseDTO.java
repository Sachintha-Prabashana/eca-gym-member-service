package lk.ijse.eca.memberservice.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrainerResponseDTO extends UserResponseDTO {
    private String phone;
    private String specialization;
}
