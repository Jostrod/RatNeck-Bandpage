package no.ratneck.backend.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import no.ratneck.backend.common.*;

import java.math.BigDecimal;

@Data
public class MerchDTO {

    private Long id;

    @Enumerated(EnumType.STRING)
    private MerchType merchType;

    private BigDecimal price;

    private boolean quantity;

    @Enumerated(EnumType.STRING)
    private Size size;


    public MerchDTO(Long id, MerchType merchType, BigDecimal price, boolean quantity, Size size) {
        this.id = id;
        this.merchType = merchType;
        this.price = price;
        this.quantity = quantity;
        this.size = size;
    }
}
