package no.ratneck.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public record ConcertDTO(
        Long id,

        String venue,

        String city,

        LocalDateTime date,

        BigDecimal ticketPrice,

        String ticketLink
) {
}
