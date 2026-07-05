package no.ratneck.backend.controller;


import no.ratneck.backend.entity.Concert;
import no.ratneck.backend.dto.ConcertDTO;
import no.ratneck.backend.service.ConcertService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/concerts")
public class ConcertController {

    private final ConcertService concertService;



    public ConcertController(ConcertService concertService) {
        this.concertService = concertService;
    }


    @PostMapping()
    public ConcertDTO createConcert(@RequestBody Concert concert){
        return concertService.createConcert(concert);
    }

    @GetMapping("/{id}")
    public ConcertDTO getConcert(@PathVariable Long id){
        return concertService.getConcertById(id);

    }


    @GetMapping
    public List<ConcertDTO> getConcerts(){
        return concertService.getAllConcerts();
    }

    @PutMapping("/{id}")
    public ConcertDTO updateConcert(@PathVariable Long id, @RequestBody Concert concert){
        return concertService.updateConcert(id, concert);
    }

    @DeleteMapping("/{id}")
    public String deleteConcert(@PathVariable Long id){
        concertService.deleteConcert(id);
        return "Concert deleted!";
    }


}
