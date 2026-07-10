package no.ratneck.backend.controller;


import jakarta.validation.Valid;
import no.ratneck.backend.dto.ConcertRequestDTO;

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
    public ConcertDTO createConcert(@Valid @RequestBody ConcertRequestDTO concertRequestDTO){
        return concertService.createConcert(concertRequestDTO);
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
    public ConcertDTO updateConcert( @PathVariable Long id, @Valid @RequestBody ConcertRequestDTO concertRequestDTO){
        return concertService.updateConcert(id, concertRequestDTO);
    }

    @DeleteMapping("/{id}")
    public String deleteConcert(@PathVariable Long id){
        concertService.deleteConcert(id);
        return "Concert deleted!";
    }


}
