package NeoBivago.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import lombok.Getter;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @Getter
    public static class ApiErrorResponse {

        private String status;
        private String message;
        private String errorCode;
        private String timestamp;

        public ApiErrorResponse(String status, String message, String errorCode) {
            this.status = status;
            this.message = message;
            this.errorCode = errorCode;
            this.timestamp = LocalDateTime.now().toString();
        }

    }

    public ResponseEntity<ApiErrorResponse> handleValidationExceptions(MethodArgumentNotValidException e) {
        ApiErrorResponse response = new ApiErrorResponse("BAD_REQUEST", "Validation failed: " + e.getMessage(), "VALIDATION_ERROR");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Invalid Credentials
    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public ResponseEntity<ApiErrorResponse> handleAuthenticationException(AuthenticationException e) {
        ApiErrorResponse response = new ApiErrorResponse("UNAUTHORIZED", "Invalid Credentials", "AUTHENTICATION_ERROR");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    // Conflict
    @ExceptionHandler(ExistingAttributeException.class)
    @ResponseBody
    public ResponseEntity<ApiErrorResponse> handleExistingAttributeException(ExistingAttributeException e) {
        ApiErrorResponse response = new ApiErrorResponse("CONFLICT", e.getMessage(), "ATTRIBUTE_EXISTS");
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    // Invalid Field
    @ExceptionHandler(LenghtException.class)
    @ResponseBody
    public ResponseEntity<ApiErrorResponse> handleLenghtException(LenghtException e) {
        ApiErrorResponse response = new ApiErrorResponse("UNPROCESSABLE_ENTITY", e.getMessage(), "INVALID_LENGTH");
        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    // Invalid Reservation Date
    @ExceptionHandler(UnauthorizedDateException.class)
    @ResponseBody
    public ResponseEntity<ApiErrorResponse> handleUnauthorizedDateException(UnauthorizedDateException e) {
        ApiErrorResponse response = new ApiErrorResponse("FORBIDDEN", e.getMessage(), "UNAUTHORIZED_DATE");
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    // Unconscious Data
    @ExceptionHandler(InvalidAttributeException.class)
    @ResponseBody
    public ResponseEntity<ApiErrorResponse> handleInvalidAttributeException(InvalidAttributeException e) {
        ApiErrorResponse response = new ApiErrorResponse("BAD_REQUEST", e.getMessage(), "INVALID_ATTRIBUTE");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Resource not Found
    @ExceptionHandler(ResponseStatusException.class)
    @ResponseBody
    public ResponseEntity<ApiErrorResponse> handleResponseStatusException(ResponseStatusException e) {
        String message = "Resource not found.";
        String errorCode = "RESOURCE_NOT_FOUND";

        if (e.getMessage().contains("User")) message = "User not found.";        
        
        if (e.getMessage().contains("Hotel")) message = "Hotel not found.";
        
        if (e.getMessage().contains("Room")) message = "Room not found.";
        
        if (e.getMessage().contains("Reservation")) message = "Reservation not found.";

        ApiErrorResponse error = new ApiErrorResponse("NOT_FOUND", message, errorCode);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // Generic
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ApiErrorResponse> handleGenericException(Exception e) {
        ApiErrorResponse error = new ApiErrorResponse("BAD_REQUEST", e.getMessage(), "GENERIC_ERROR");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
