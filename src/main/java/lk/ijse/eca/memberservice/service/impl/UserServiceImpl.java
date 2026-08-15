package lk.ijse.eca.memberservice.service.impl;

import lk.ijse.eca.memberservice.dto.request.UserUpdateRequestDTO;
import lk.ijse.eca.memberservice.dto.response.MemberResponseDTO;
import lk.ijse.eca.memberservice.dto.response.TrainerResponseDTO;
import lk.ijse.eca.memberservice.dto.response.UserResponseDTO;
import lk.ijse.eca.memberservice.entity.Member;
import lk.ijse.eca.memberservice.entity.Role;
import lk.ijse.eca.memberservice.entity.Trainer;
import lk.ijse.eca.memberservice.entity.User;
import lk.ijse.eca.memberservice.mapper.UserMapper;
import lk.ijse.eca.memberservice.repository.MemberRepository;
import lk.ijse.eca.memberservice.repository.TrainerRepository;
import lk.ijse.eca.memberservice.repository.UserRepository;
import lk.ijse.eca.memberservice.service.FileStorageService;
import lk.ijse.eca.memberservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final TrainerRepository trainerRepository;
    private final FileStorageService fileStorageService;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return switch (user.getRole()) {
            case ADMIN -> mapToAdminResponse(user);
            case MEMBER -> mapToMemberResponse(user);
            case TRAINER -> mapToTrainerResponse(user);
        };
    }

    @Override
    @Transactional
    public UserResponseDTO updateUserDetails(Long userId, UserUpdateRequestDTO requestDTO, MultipartFile file) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (requestDTO != null) {
            if (requestDTO.getFirstName() != null) user.setFirstName(requestDTO.getFirstName());
            if (requestDTO.getLastName() != null) user.setLastName(requestDTO.getLastName());

            switch (user.getRole()) {
                case MEMBER -> updateMemberSpecificDetails(user, requestDTO);
                case TRAINER -> updateTrainerSpecificDetails(user, requestDTO);
                case ADMIN -> {} // Nothing extra to update for admin currently
            }
            user.setUpdatedAt(LocalDateTime.now());
            userRepository.save(user);
        }

        if (file != null && !file.isEmpty()) {
            String imageUrl = fileStorageService.storeFile(file);
            user.setProfileImageUrl(imageUrl);
            user.setUpdatedAt(LocalDateTime.now());
            userRepository.save(user);
        }

        return getUserProfile(userId);
    }

    private UserResponseDTO mapToAdminResponse(User user) {
        return userMapper.toUserResponseDTO(user);
    }

    private MemberResponseDTO mapToMemberResponse(User user) {
        Member member = memberRepository.findByUserId(user.getId()).orElse(null);
        return userMapper.toMemberResponseDTO(user, member);
    }

    private TrainerResponseDTO mapToTrainerResponse(User user) {
        Trainer trainer = trainerRepository.findByUserId(user.getId()).orElse(null);
        return userMapper.toTrainerResponseDTO(user, trainer);
    }

    private void updateMemberSpecificDetails(User user, UserUpdateRequestDTO requestDTO) {
        memberRepository.findByUserId(user.getId()).ifPresent(member -> {
            if (requestDTO.getPhone() != null) member.setPhone(requestDTO.getPhone());
            if (requestDTO.getDateOfBirth() != null) member.setDateOfBirth(requestDTO.getDateOfBirth());
            if (requestDTO.getGender() != null) member.setGender(requestDTO.getGender());
            memberRepository.save(member);
        });
    }

    private void updateTrainerSpecificDetails(User user, UserUpdateRequestDTO requestDTO) {
        trainerRepository.findByUserId(user.getId()).ifPresent(trainer -> {
            if (requestDTO.getPhone() != null) trainer.setPhone(requestDTO.getPhone());
            if (requestDTO.getSpecialization() != null) trainer.setSpecialization(requestDTO.getSpecialization());
            trainerRepository.save(trainer);
        });
    }
}
