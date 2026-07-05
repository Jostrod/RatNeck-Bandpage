package no.ratneck.backend.service;


import no.ratneck.backend.dto.ConcertRequestDTO;
import no.ratneck.backend.entity.Concert;
import no.ratneck.backend.dto.ConcertDTO;
import no.ratneck.backend.exception.ConcertNotFoundException;
import no.ratneck.backend.repository.ConcertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Concert foundConcert = concertRepository.findById(id).orElseThrow(() -> new ConcertNotFoundException("No concert found with ID " + id));

        return new ConcertDTO(foundConcert.getVenue(), foundConcert.getCity(),
                foundConcert.getDate(), foundConcert.getTicketPrice(), foundConcert.getTicketLink());
    }

    public ConcertDTO createConcert(ConcertRequestDTO requestDTO){

        Concert concert = new Concert();
        concert.setCity(requestDTO.getCity());
        concert.setDate(requestDTO.getDate());
        concert.setVenue(requestDTO.getVenue());
        concert.setTicketPrice(requestDTO.getTicketPrice());
        concert.setTicketLink(requestDTO.getTicketLink());


        Concert savedConcert = concertRepository.save(concert);

        return new ConcertDTO(savedConcert.getVenue(), savedConcert.getCity(),
                savedConcert.getDate(), savedConcert.getTicketPrice(), savedConcert.getTicketLink());

    }

    public void deleteConcert(Long id){
        concertRepository.deleteById(id);
    }

    public ConcertDTO updateConcert(Long id, ConcertRequestDTO requestDTO){
        Concert existingConcert = concertRepository.findById(id).orElseThrow(() -> new ConcertNotFoundException("No concert found with id " + id));

        existingConcert.setCity(requestDTO.getCity());
        existingConcert.setDate(requestDTO.getDate());
        existingConcert.setVenue(requestDTO.getVenue());
        existingConcert.setTicketPrice(requestDTO.getTicketPrice());
        existingConcert.setTicketLink(requestDTO.getTicketLink());
        concertRepository.save(existingConcert);
        return new ConcertDTO(existingConcert.getVenue(), existingConcert.getCity(),
                existingConcert.getDate(), existingConcert.getTicketPrice(), existingConcert.getTicketLink());

    }


}
