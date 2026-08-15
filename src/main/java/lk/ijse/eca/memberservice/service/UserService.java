package lk.ijse.eca.memberservice.service;

import lk.ijse.eca.memberservice.dto.request.UserUpdateRequestDTO;
import lk.ijse.eca.memberservice.dto.response.UserResponseDTO;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    UserResponseDTO getUserProfile(Long userId);
    UserResponseDTO updateUserDetails(Long userId, UserUpdateRequestDTO requestDTO, MultipartFile file);
}
