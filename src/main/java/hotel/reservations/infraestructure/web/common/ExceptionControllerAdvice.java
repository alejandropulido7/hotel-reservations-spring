package hotel.reservations.infraestructure.web.common;

import hotel.reservations.domain.exceptions.ApplicationException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ResponseDTO> applicationException(ApplicationException ex){

        ResponseDTO responseDTO = ResponseDTO.builder()
                .message(ex.getMessage())
                .status(HttpStatus.BAD_GATEWAY.value())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(responseDTO);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(WebExchangeBindException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        Map<String, Object> response = new HashMap<>();
        response.put("data", errors);
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("message", "Validation error occurred");

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
