package lk.ijse.eca.memberservice.service.impl;

import lk.ijse.eca.memberservice.dto.request.LoginRequestDTO;
import lk.ijse.eca.memberservice.dto.response.MemberDetailsResponseDTO;
import lk.ijse.eca.memberservice.dto.request.MemberRegisterRequestDTO;
import lk.ijse.eca.memberservice.dto.request.TrainerCreateRequestDTO;
import lk.ijse.eca.memberservice.dto.response.AuthResponseDTO;
import lk.ijse.eca.memberservice.dto.response.TrainerResponseDTO;
import lk.ijse.eca.memberservice.dto.response.UserResponseDTO;
import lk.ijse.eca.memberservice.entity.Member;
import lk.ijse.eca.memberservice.entity.Role;
import lk.ijse.eca.memberservice.entity.Trainer;
import lk.ijse.eca.memberservice.entity.User;
import lk.ijse.eca.memberservice.exception.DuplicateResourceException;
import lk.ijse.eca.memberservice.mapper.MemberMapper;
import lk.ijse.eca.memberservice.mapper.UserMapper;
import lk.ijse.eca.memberservice.repository.MemberRepository;
import lk.ijse.eca.memberservice.repository.TrainerRepository;
import lk.ijse.eca.memberservice.repository.UserRepository;
import lk.ijse.eca.memberservice.service.FileStorageService;
import lk.ijse.eca.memberservice.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import lk.ijse.eca.memberservice.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final UserRepository userRepository;
    private final MemberMapper memberMapper;
    private final FileStorageService fileStorageService;
    private final TrainerRepository trainerRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public UserResponseDTO registerMember(MemberRegisterRequestDTO requestDTO) {
        // 1. Check if email already exists
        if (userRepository.existsByEmail(requestDTO.getEmail())) {
            throw new DuplicateResourceException("Email is already in use");
        }

        // 2. Map DTO to User entity and set defaults
        User user = memberMapper.toUserEntity(requestDTO);
        user.setRole(Role.MEMBER);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        
        // Encrypt the password before saving
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));

        // 3. Save User
        user = userRepository.save(user);

        // 4. Map DTO to Member entity
        Member member = memberMapper.toMemberEntity(requestDTO);
        member.setUser(user);

        // 5. Save Member
        member = memberRepository.save(member);

        // 6. Return mapped response
        return memberMapper.toUserResponseDTO(user);
    }

    @Override
    @Transactional
    public void uploadProfileImage(Long memberId, MultipartFile file) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        
        if (file != null && !file.isEmpty()) {
            String imageUrl = fileStorageService.storeFile(file);
            User user = member.getUser();
            user.setProfileImageUrl(imageUrl);
            user.setUpdatedAt(LocalDateTime.now());
            userRepository.save(user);
        }
    }

    @Override
    @Transactional
    public TrainerResponseDTO createTrainer(TrainerCreateRequestDTO requestDTO) {
        // 1. Check email
        if (userRepository.existsByEmail(requestDTO.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        User user = new User();
        user.setFirstName(requestDTO.getFirstName());
        user.setLastName(requestDTO.getLastName());
        user.setEmail(requestDTO.getEmail());
        user.setRole(Role.TRAINER);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        
        user = userRepository.save(user);

        Trainer trainer = new Trainer();
        trainer.setPhone(requestDTO.getPhone());
        trainer.setSpecialization(requestDTO.getSpecialization());
        trainer.setUser(user);
        
        trainer = trainerRepository.save(trainer);

        return userMapper.toTrainerResponseDTO(user, trainer);
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO requestDTO) {
        User user = userRepository.findByEmail(requestDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(requestDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String accessToken = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole().name());
        String refreshToken = jwtUtil.generateRefreshToken(user.getId(), user.getEmail());
        
        return new AuthResponseDTO(accessToken, refreshToken, userMapper.toUserResponseDTO(user));
    }

    @Override
    public AuthResponseDTO refresh(String refreshToken) {
        String email = jwtUtil.extractEmail(refreshToken);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!jwtUtil.validateToken(refreshToken, email)) {
            throw new RuntimeException("Invalid refresh token");
        }

        String newAccessToken = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole().name());
        
        return new AuthResponseDTO(newAccessToken, refreshToken, userMapper.toUserResponseDTO(user));
    }

//    @Override
//    @Transactional
//    public UserResponseDTO promoteToTrainer(Long memberId) {
//        Member member = memberRepository.findById(memberId)
//                .orElseThrow(() -> new RuntimeException("Member not found"));
//
//        User user = member.getUser();
//        if (user.getRole() == Role.TRAINER) {
//            throw new RuntimeException("User is already a trainer");
//        }
//
//        user.setRole(Role.TRAINER);
//        user.setUpdatedAt(LocalDateTime.now());
//        userRepository.save(user);
//
//        return memberMapper.toUserResponseDTO(user);
//    }

    @Override
    @Transactional
    public MemberDetailsResponseDTO assignTrainer(Long memberId, Long trainerId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        if (member.getTrainer() != null) {
            throw new RuntimeException("Member already has a trainer assigned");
        }

        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));

        member.setTrainer(trainer);
        memberRepository.save(member);

        return userMapper.toMemberDetailsResponseDTO(member);
    }

    @Override
    @Transactional(readOnly = true)
    public MemberDetailsResponseDTO getAssignedTrainer(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        return userMapper.toMemberDetailsResponseDTO(member);
    }

    @Override
    @Transactional(readOnly = true)
    public java.util.List<MemberDetailsResponseDTO> getMembersByTrainer(Long trainerId) {
        if (!trainerRepository.existsById(trainerId)) {
            throw new RuntimeException("Trainer not found");
        }
        
        java.util.List<Member> members = memberRepository.findByTrainerId(trainerId);
        return members.stream()
                .map(userMapper::toMemberDetailsResponseDTO)
                .collect(java.util.stream.Collectors.toList());
    }
}
