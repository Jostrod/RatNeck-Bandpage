package no.ratneck.backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import no.ratneck.backend.common.MerchType;
import no.ratneck.backend.common.Size;

import java.math.BigDecimal;

@Data
public class MerchRequestDTO {

    @NotNull
    private MerchType merchType;

    @Positive
    private BigDecimal price;

    @PositiveOrZero
    private int quantity;

    @NotNull
    private Size size;

}
