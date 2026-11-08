package no.ratneck.backend.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;


import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ConcertRequestDTO(

        @NotBlank String venue,

        @NotBlank String city,

        @NotNull LocalDateTime date,

        @PositiveOrZero @NotNull BigDecimal ticketPrice,

        @NotBlank String ticketLink
) {}
