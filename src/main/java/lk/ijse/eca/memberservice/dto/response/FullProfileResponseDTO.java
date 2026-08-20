package lk.ijse.eca.memberservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullProfileResponseDTO {
    private Long id;
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String profileImageUrl;
    private String phone;
    private LocalDate dateOfBirth;
    private String gender;
    private TrainerSummaryDTO assignedTrainer;
}
