package no.ratneck.backend.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;


import java.time.LocalDateTime;

@Data
public class ConcertRequestDTO {

    @NotBlank
    private String venue;
    @NotBlank
    private String city;
    @NotNull
    private LocalDateTime date;
    @Positive
    private double ticketPrice;
    @NotBlank
    private String ticketLink;

}
