package lk.ijse.eca.memberservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerSummaryDTO {
    private Long id;
    private Long userId;
    private String firstName;
    private String lastName;
    private String specialization;
}
