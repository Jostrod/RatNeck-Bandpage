package no.ratneck.backend.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

import no.ratneck.backend.common.MerchType;
import no.ratneck.backend.common.Size;
@Getter
@Setter
@Entity
@Table
public class Merch {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MerchType merchType;

    private BigDecimal price;

    private int quantity;

    @Enumerated(EnumType.STRING)
    private Size size;
}
