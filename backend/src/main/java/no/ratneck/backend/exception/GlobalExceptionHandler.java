package no.ratneck.backend.exception;


import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ConcertNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleConcertNotFound(ConcertNotFoundException exception, HttpServletRequest request){
        logger.warn("{} ", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleInvalidData(MethodArgumentNotValidException methodArgumentNotValidException){
        Map<String, String> response = new HashMap<>();

        List<FieldError> errors = methodArgumentNotValidException.getBindingResult().getFieldErrors();

        for (FieldError error:errors) {
            String field = error.getField();
            String message = error.getDefaultMessage();
            response.put(field, message);
        }

        logger.warn("Validation failed: {}", response);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

    }
}
