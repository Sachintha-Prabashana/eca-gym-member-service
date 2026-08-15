package lk.ijse.eca.memberservice.service;

import jakarta.validation.Valid;
import lk.ijse.eca.memberservice.dto.request.MemberRegisterRequestDTO;
import lk.ijse.eca.memberservice.dto.request.TrainerCreateRequestDTO;
import lk.ijse.eca.memberservice.dto.response.TrainerResponseDTO;
import lk.ijse.eca.memberservice.dto.response.UserResponseDTO;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {
    UserResponseDTO registerMember(MemberRegisterRequestDTO requestDTO);
    void uploadProfileImage(Long memberId, MultipartFile file);

    TrainerResponseDTO createTrainer(TrainerCreateRequestDTO requestDTO);

    lk.ijse.eca.memberservice.dto.response.AuthResponseDTO login(lk.ijse.eca.memberservice.dto.request.LoginRequestDTO requestDTO);
    lk.ijse.eca.memberservice.dto.response.AuthResponseDTO refresh(String refreshToken);

    lk.ijse.eca.memberservice.dto.response.MemberDetailsResponseDTO assignTrainer(Long memberId, Long trainerId);
    lk.ijse.eca.memberservice.dto.response.MemberDetailsResponseDTO getAssignedTrainer(Long memberId);
    java.util.List<lk.ijse.eca.memberservice.dto.response.MemberDetailsResponseDTO> getMembersByTrainer(Long trainerId);
}
