package no.ratneck.backend.service;


import no.ratneck.backend.entity.Concert;
import no.ratneck.backend.entity.ConcertDTO;
import no.ratneck.backend.repository.ConcertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConcertService {

    private final ConcertRepository concertRepository;



    @Autowired
    public ConcertService(ConcertRepository concertRepository) {
        this.concertRepository = concertRepository;
    }

    public List<ConcertDTO> getAllConcerts(){
        List<Concert> concerts = concertRepository.findAll();

        return concerts.stream().map(concert -> new ConcertDTO(
                concert.getVenue(), concert.getCity(), concert.getDate(), concert.getTicketPrice(), concert.getTicketLink()
                )).toList();
    }

    public ConcertDTO getConcertById(Long id){
        Concert foundConcert = concertRepository.findById(id).orElseThrow();

        return new ConcertDTO(foundConcert.getVenue(), foundConcert.getCity(),
                foundConcert.getDate(), foundConcert.getTicketPrice(), foundConcert.getTicketLink());
    }

    public ConcertDTO createConcert(Concert concert){
        Concert savedConcert = concertRepository.save(concert);

        return new ConcertDTO(savedConcert.getVenue(), savedConcert.getCity(),
                savedConcert.getDate(), savedConcert.getTicketPrice(), savedConcert.getTicketLink());

    }

    public void deleteConcert(Long id){
        concertRepository.deleteById(id);
    }

    public ConcertDTO updateConcert(Long id, Concert concert){
        Concert existingConcert = concertRepository.findById(id).orElseThrow();

        existingConcert.setCity(concert.getCity());
        existingConcert.setDate(concert.getDate());
        existingConcert.setVenue(concert.getVenue());
        existingConcert.setTicketPrice(concert.getTicketPrice());
        existingConcert.setTicketLink(concert.getTicketLink());
        concertRepository.save(existingConcert);
        return new ConcertDTO(existingConcert.getVenue(), existingConcert.getCity(),
                existingConcert.getDate(), existingConcert.getTicketPrice(), existingConcert.getTicketLink());

    }


}
