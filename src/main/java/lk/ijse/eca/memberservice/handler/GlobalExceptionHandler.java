package lk.ijse.eca.memberservice.handler;

import jakarta.servlet.http.HttpServletRequest;
import lk.ijse.eca.memberservice.dto.response.ApiResponse;
import lk.ijse.eca.memberservice.exception.AuthenticationFailedException;
import lk.ijse.eca.memberservice.exception.DuplicateResourceException;
import lk.ijse.eca.memberservice.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        return new ResponseEntity<>(
                ApiResponse.<Void>builder()
                        .success(false)
                        .data(ApiResponse.DataWrapper.<Void>builder()
                                .message("Resource Not Found")
                                .error(ex.getMessage())
                                .build())
                        .status(HttpStatus.NOT_FOUND.value())
                        .path(request.getRequestURI())
                        .build(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAuthenticationFailedException(AuthenticationFailedException ex, HttpServletRequest request) {
        return new ResponseEntity<>(
                ApiResponse.<Void>builder()
                        .success(false)
                        .data(ApiResponse.DataWrapper.<Void>builder()
                                .message("Authentication Failed")
                                .error(ex.getMessage())
                                .build())
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .path(request.getRequestURI())
                        .build(),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicateResourceException(DuplicateResourceException ex, HttpServletRequest request) {
        return new ResponseEntity<>(
                ApiResponse.<Void>builder()
                        .success(false)
                        .data(ApiResponse.DataWrapper.<Void>builder()
                                .message("Duplicate Resource")
                                .error(ex.getMessage())
                                .build())
                        .status(HttpStatus.CONFLICT.value())
                        .path(request.getRequestURI())
                        .build(),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(
                ApiResponse.<Map<String, String>>builder()
                        .success(false)
                        .data(ApiResponse.DataWrapper.<Map<String, String>>builder()
                                .message("Validation Failed")
                                .error(errors)
                                .build())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .path(request.getRequestURI())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGlobalException(Exception ex, HttpServletRequest request) {
        return new ResponseEntity<>(
                ApiResponse.<Void>builder()
                        .success(false)
                        .data(ApiResponse.DataWrapper.<Void>builder()
                                .message("Internal Server Error")
                                .error("An unexpected error occurred")
                                .build())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .path(request.getRequestURI())
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
