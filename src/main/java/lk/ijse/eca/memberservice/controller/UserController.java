package lk.ijse.eca.memberservice.controller;

import jakarta.validation.Valid;
import lk.ijse.eca.memberservice.dto.request.UserUpdateRequestDTO;
import lk.ijse.eca.memberservice.dto.response.ApiResponse;
import lk.ijse.eca.memberservice.dto.response.UserResponseDTO;
import lk.ijse.eca.memberservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUserProfile(@PathVariable Long userId) {
        UserResponseDTO response = userService.getUserProfile(userId);
        return new ResponseEntity<>(
                ApiResponse.success("Profile retrieved successfully", response),
                HttpStatus.OK
        );
    }

    @PatchMapping(value = "/{userId}/details", consumes = {"multipart/form-data"})
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateUserDetails(
            @PathVariable Long userId,
            @Valid @ModelAttribute UserUpdateRequestDTO requestDTO,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        UserResponseDTO response = userService.updateUserDetails(userId, requestDTO, file);
        return new ResponseEntity<>(
                ApiResponse.success("User details updated successfully", response),
                HttpStatus.OK
        );
    }
}
