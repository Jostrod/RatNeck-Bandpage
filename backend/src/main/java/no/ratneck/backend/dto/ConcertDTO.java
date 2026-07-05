package no.ratneck.backend.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConcertDTO {
    private String venue;

    private String city;

    private LocalDateTime date;

    private double ticketPrice;

    private String ticketLink;

    public ConcertDTO(String venue, String city, LocalDateTime date, double ticketPrice, String ticketLink) {
        this.venue = venue;
        this.city = city;
        this.date = date;
        this.ticketPrice = ticketPrice;
        this.ticketLink = ticketLink;
    }
}
