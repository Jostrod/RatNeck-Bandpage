package no.ratneck.backend.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table
public class Concert {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String venue;

    private String city;

    private LocalDateTime date;

    private double ticketPrice;

    private String ticketLink;

}
