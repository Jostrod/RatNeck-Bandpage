package no.ratneck.backend.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
public class Concert {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String venue;

    private String city;

    private LocalDateTime date;

    private BigDecimal ticketPrice;

    private String ticketLink;

}
