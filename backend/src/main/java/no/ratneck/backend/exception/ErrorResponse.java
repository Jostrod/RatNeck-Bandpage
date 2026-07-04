package no.ratneck.backend.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {

    private int status;
    private LocalDateTime time;
    private String error;
    private String message;
    private String path;

    public ErrorResponse(int status, LocalDateTime time, String error, String message, String path) {
        this.status = status;
        this.time = time;
        this.error = error;
        this.message = message;
        this.path = path;
    }


}
