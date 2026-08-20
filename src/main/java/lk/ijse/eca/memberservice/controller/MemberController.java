package lk.ijse.eca.memberservice.controller;

import jakarta.validation.Valid;
import lk.ijse.eca.memberservice.dto.request.LoginRequestDTO;
import lk.ijse.eca.memberservice.dto.request.MemberRegisterRequestDTO;
import lk.ijse.eca.memberservice.dto.request.TrainerCreateRequestDTO;
import lk.ijse.eca.memberservice.dto.request.UserUpdateRequestDTO;
import lk.ijse.eca.memberservice.dto.response.*;
import lk.ijse.eca.memberservice.service.MemberService;
import lk.ijse.eca.memberservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponseDTO>> registerMember(@Valid @RequestBody MemberRegisterRequestDTO requestDTO) {

        UserResponseDTO responseDTO = memberService.registerMember(requestDTO);
        return new ResponseEntity<>(
                ApiResponse.success("Member registered successfully", responseDTO),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> login(@Valid @RequestBody LoginRequestDTO requestDTO) {

        AuthResponseDTO responseDTO = memberService.login(requestDTO);
        return new ResponseEntity<>(
                ApiResponse.success("Login successful", responseDTO),
                HttpStatus.OK
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> refresh(@RequestParam String refreshToken) {

        AuthResponseDTO responseDTO = memberService.refresh(refreshToken);
        return new ResponseEntity<>(
                ApiResponse.success("Token refreshed successfully", responseDTO),
                HttpStatus.OK
        );
    }

    @PostMapping(value = "/{memberId}/profile-image", consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse<Void>> uploadProfileImage(
            @PathVariable Long memberId,
            @RequestParam("file") MultipartFile file) {

        memberService.uploadProfileImage(memberId, file);
        return new ResponseEntity<>(
                ApiResponse.success("Profile image uploaded successfully", null),
                HttpStatus.OK
        );
    }

    @PostMapping("/trainers")
    public ResponseEntity<ApiResponse<TrainerResponseDTO>> createTrainer(
            @Valid @RequestBody TrainerCreateRequestDTO requestDTO
    ) {
        TrainerResponseDTO responseDTO = memberService.createTrainer(requestDTO);

        return new ResponseEntity<>(
                ApiResponse.success("Trainer successfully created", responseDTO),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{memberId}/trainer/{trainerId}")
    public ResponseEntity<ApiResponse<MemberDetailsResponseDTO>> assignTrainer(
            @PathVariable Long memberId,
            @PathVariable Long trainerId) {
        
        MemberDetailsResponseDTO responseDTO = memberService.assignTrainer(memberId, trainerId);
        return new ResponseEntity<>(
                ApiResponse.success("Trainer assigned successfully", responseDTO),
                HttpStatus.OK
        );
    }

    @GetMapping("/{memberId}/trainer")
    public ResponseEntity<ApiResponse<MemberDetailsResponseDTO>> getAssignedTrainer(@PathVariable Long memberId) {
        MemberDetailsResponseDTO responseDTO = memberService.getAssignedTrainer(memberId);

        return new ResponseEntity<>(
                ApiResponse.success("Assigned trainer retrieved successfully", responseDTO),
                HttpStatus.OK
        );
    }

    @GetMapping("/{memberId}/full-profile")
    public ResponseEntity<ApiResponse<FullProfileResponseDTO>> getFullProfile(@PathVariable Long memberId) {
        FullProfileResponseDTO responseDTO = memberService.getFullProfile(memberId);

        return new ResponseEntity<>(
                ApiResponse.success("Full profile retrieved successfully", responseDTO),
                HttpStatus.OK
        );
    }

}
