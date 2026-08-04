package no.ratneck.backend.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record OrderRequestDTO(@NotEmpty @Valid List<OrderLineRequestDTO> lines) {
}
