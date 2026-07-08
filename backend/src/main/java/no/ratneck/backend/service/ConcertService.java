package no.ratneck.backend.service;


import no.ratneck.backend.dto.ConcertRequestDTO;
import no.ratneck.backend.entity.Concert;
import no.ratneck.backend.dto.ConcertDTO;
import no.ratneck.backend.exception.ConcertNotFoundException;
import no.ratneck.backend.repository.ConcertRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConcertService {

    private final ConcertRepository concertRepository;
    private static final Logger logger = LoggerFactory.getLogger(ConcertService.class);


    @Autowired
    public ConcertService(ConcertRepository concertRepository) {
        this.concertRepository = concertRepository;
    }

    public List<ConcertDTO> getAllConcerts(){

        List<Concert> concerts = concertRepository.findAll();
        logger.info("Returned all concerts");
        return concerts.stream().map(concert -> new ConcertDTO(
                concert.getId(), concert.getVenue(), concert.getCity(), concert.getDate(), concert.getTicketPrice(), concert.getTicketLink()
                )).toList();
    }

    public ConcertDTO getConcertById(Long id){
        Concert foundConcert = concertRepository.findById(id).orElseThrow(() -> new ConcertNotFoundException("No concert found with ID " + id));

        logger.info("Concert retrieved with id {}", id);
        return new ConcertDTO(foundConcert.getId(), foundConcert.getVenue(), foundConcert.getCity(),
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
        logger.info("Concert created with id: {}: {}", savedConcert.getId(), savedConcert.getVenue());
        return new ConcertDTO(savedConcert.getId(), savedConcert.getVenue(), savedConcert.getCity(),
                savedConcert.getDate(), savedConcert.getTicketPrice(), savedConcert.getTicketLink());

    }

    public void deleteConcert(Long id){
        Concert foundConcert = concertRepository.findById(id).orElseThrow(() -> new ConcertNotFoundException("No concert found with id " + id));
        concertRepository.deleteById(foundConcert.getId());
        logger.info("Concert with id: {} was successfully deleted", id);
    }

    public ConcertDTO updateConcert(Long id, ConcertRequestDTO requestDTO){
        Concert existingConcert = concertRepository.findById(id).orElseThrow(() -> new ConcertNotFoundException("No concert found with id " + id));

        existingConcert.setCity(requestDTO.getCity());
        existingConcert.setDate(requestDTO.getDate());
        existingConcert.setVenue(requestDTO.getVenue());
        existingConcert.setTicketPrice(requestDTO.getTicketPrice());
        existingConcert.setTicketLink(requestDTO.getTicketLink());
        concertRepository.save(existingConcert);
        logger.info("Concert with id {} successfully updated", existingConcert.getId());
        return new ConcertDTO(existingConcert.getId(), existingConcert.getVenue(), existingConcert.getCity(),
                existingConcert.getDate(), existingConcert.getTicketPrice(), existingConcert.getTicketLink());

    }


}
