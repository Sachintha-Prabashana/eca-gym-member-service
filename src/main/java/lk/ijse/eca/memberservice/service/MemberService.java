package lk.ijse.eca.memberservice.service;

import lk.ijse.eca.memberservice.dto.request.MemberRegisterRequestDTO;
import lk.ijse.eca.memberservice.dto.request.TrainerCreateRequestDTO;
import lk.ijse.eca.memberservice.dto.response.AuthResponseDTO;
import lk.ijse.eca.memberservice.dto.response.MemberDetailsResponseDTO;
import lk.ijse.eca.memberservice.dto.response.TrainerResponseDTO;
import lk.ijse.eca.memberservice.dto.response.UserResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MemberService {
    UserResponseDTO registerMember(MemberRegisterRequestDTO requestDTO);
    void uploadProfileImage(Long memberId, MultipartFile file);

    TrainerResponseDTO createTrainer(TrainerCreateRequestDTO requestDTO);

    AuthResponseDTO login(lk.ijse.eca.memberservice.dto.request.LoginRequestDTO requestDTO);
    AuthResponseDTO refresh(String refreshToken);

    MemberDetailsResponseDTO assignTrainer(Long memberId, Long trainerId);
    MemberDetailsResponseDTO getAssignedTrainer(Long memberId);
    List<MemberDetailsResponseDTO> getMembersByTrainer(Long trainerId);
}
