package lk.ijse.eca.memberservice.controller;

import lk.ijse.eca.memberservice.dto.response.ApiResponse;
import lk.ijse.eca.memberservice.dto.response.MemberDetailsResponseDTO;
import lk.ijse.eca.memberservice.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trainers")
@RequiredArgsConstructor
public class TrainerController {

    private final MemberService memberService;

    @GetMapping("/{trainerId}/members")
    public ResponseEntity<ApiResponse<List<MemberDetailsResponseDTO>>> getMembersByTrainer(@PathVariable Long trainerId) {
        List<MemberDetailsResponseDTO> members = memberService.getMembersByTrainer(trainerId);
        return new ResponseEntity<>(
                ApiResponse.success("Assigned members retrieved successfully", members),
                HttpStatus.OK
        );
    }
}
