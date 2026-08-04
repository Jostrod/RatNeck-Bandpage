package no.ratneck.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDTO(Long id, LocalDateTime createdAt, List<OrderLineResponseDTO> lineDTOs, BigDecimal total) {

}
