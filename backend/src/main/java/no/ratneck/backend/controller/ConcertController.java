package no.ratneck.backend.controller;


import no.ratneck.backend.entity.Concert;
import no.ratneck.backend.service.ConcertService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/concerts")
public class ConcertController {

    private final ConcertService concertService;


    public ConcertController(ConcertService concertService ) {
        this.concertService = concertService;
    }


    @PostMapping()
    public Concert createConcert(@RequestBody Concert concert){
        return concertService.createConcert(concert);
    }

    @GetMapping("/{id}")
    public Optional<Concert> getConcert(@PathVariable Long id){
        return concertService.getConcertById(id);
    }

    @GetMapping
    public List<Concert> getConcerts(){
        return concertService.getAllConcerts();
    }

    @PutMapping("/{id}")
    public Concert updateConcert(@PathVariable Long id, @RequestBody Concert concert){
        return concertService.updateConcert(id, concert);
    }

    @DeleteMapping("/{id}")
    public String deleteConcert(@PathVariable Long id){
        concertService.deleteConcert(id);
        return "Concert deleted!";
    }


}
