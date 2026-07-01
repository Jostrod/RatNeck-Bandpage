package no.ratneck.backend.service;


import no.ratneck.backend.entity.Concert;
import no.ratneck.backend.repository.ConcertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConcertService {

    private final ConcertRepository concertRepository;


    @Autowired
    public ConcertService(ConcertRepository concertRepository) {
        this.concertRepository = concertRepository;
    }

    public List<Concert> getAllConcerts(){
        return concertRepository.findAll();
    }

    public Optional<Concert> getConcertById(Long id){
        return concertRepository.findById(id);
    }

    public Concert createConcert(Concert concert){
        return concertRepository.save(concert);

    }

    public void deleteConcert(Long id){
        concertRepository.deleteById(id);
    }

    public Concert updateConcert(Long id, Concert concert){
        Concert existingConcert = concertRepository.findById(id).orElseThrow();

        existingConcert.setCity(concert.getCity());
        existingConcert.setDate(concert.getDate());
        existingConcert.setVenue(concert.getVenue());
        existingConcert.setTicketPrice(concert.getTicketPrice());
        existingConcert.setTicketLink(concert.getTicketLink());
        concertRepository.save(existingConcert);
        return existingConcert;

    }


}
