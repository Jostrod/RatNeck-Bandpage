package no.ratneck.backend.controller;

import jakarta.validation.Valid;
import no.ratneck.backend.dto.MerchDTO;
import no.ratneck.backend.dto.MerchRequestDTO;
import no.ratneck.backend.service.MerchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/merch")
public class MerchController {

    private final MerchService merchService;

    public MerchController(MerchService merchService) {
        this.merchService = merchService;
    }


    @PostMapping
    public MerchDTO addMerch(@Valid @RequestBody MerchRequestDTO merchRequestDTO){
        return merchService.addMerch(merchRequestDTO);
    }

    @GetMapping("/{id}")
    public MerchDTO getMerchById(@PathVariable Long id){
        return merchService.getMerchById(id);
    }

    @GetMapping
    public List<MerchDTO> getAllMerch(){
        return merchService.getAllMerch();
    }

    @PutMapping("/{id}")
    public MerchDTO updateMerch(@PathVariable Long id, @Valid @RequestBody MerchRequestDTO requestDTO){
        return merchService.updateMerch(id, requestDTO);
    }

    @DeleteMapping("/{id}")
    public String deleteMerch(@PathVariable Long id){
        merchService.deleteMerch(id);
        return "Merch deleted";
    }
}
