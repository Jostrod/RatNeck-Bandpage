package no.ratneck.backend.dto;
import lombok.Data;
import no.ratneck.backend.common.*;

import java.math.BigDecimal;

@Data
public class MerchDTO {

    private Long id;

    private MerchType merchType;

    private BigDecimal price;

    private boolean inStock;

    private Size size;


    public MerchDTO(Long id, MerchType merchType, BigDecimal price, boolean inStock, Size size) {
        this.id = id;
        this.merchType = merchType;
        this.price = price;
        this.inStock = inStock;
        this.size = size;
    }

}
