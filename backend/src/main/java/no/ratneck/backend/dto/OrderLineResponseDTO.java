package no.ratneck.backend.dto;

import no.ratneck.backend.common.MerchType;

import java.math.BigDecimal;

public record OrderLineResponseDTO(
        Long productId,
        MerchType merchType,
        int quantity,
        BigDecimal priceAtPurchase
) {
}
