package ftn.tseo.eObrazovanje.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ftn.tseo.eObrazovanje.dto.PredispitnaObavezaDTO;
import ftn.tseo.eObrazovanje.service.PredispitnaObavezaServiceInterface;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping(value = "predispitnaObaveza")
public class PredispitnaObavezaController {

    @Autowired
    private final PredispitnaObavezaServiceInterface predispitnaObavezaService;

    @GetMapping
    public ResponseEntity<List<PredispitnaObavezaDTO>> getPredispitneObaveze() {
        List<PredispitnaObavezaDTO> predispitneObaveze = predispitnaObavezaService.findAll();
        return new ResponseEntity<List<PredispitnaObavezaDTO>>(predispitneObaveze, HttpStatus.OK);
    }

    @GetMapping("/prijavljeno/student/{id}")
    public ResponseEntity<List<PredispitnaObavezaDTO>> getPrijavljenePredispitneObaveze(@PathVariable Long id) {
        List<PredispitnaObavezaDTO> predispitneObaveze = predispitnaObavezaService.findPrijavljenePredispitneObaveze(id);
        return new ResponseEntity<List<PredispitnaObavezaDTO>>(predispitneObaveze, HttpStatus.OK);
    }

    @GetMapping("/neprijavljeno/student/{id}")
    public ResponseEntity<List<PredispitnaObavezaDTO>> getNeprijavljenePredispitneObaveze(@PathVariable Long id) {
        List<PredispitnaObavezaDTO> predispitneObaveze = predispitnaObavezaService.findNeprijavljenePredispitneObaveze(id);
        return new ResponseEntity<List<PredispitnaObavezaDTO>>(predispitneObaveze, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PredispitnaObavezaDTO> getPredispitnaObaveza(@PathVariable Long id) {
        PredispitnaObavezaDTO predispitnaObaveza = predispitnaObavezaService.findOne(id);
        if (predispitnaObaveza == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(predispitnaObaveza, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'NASTAVNIK')")
    @PostMapping
    public ResponseEntity<PredispitnaObavezaDTO> savePredispitnaObaveza(
            @Valid @RequestBody PredispitnaObavezaDTO predispitnaObavezaDTO) {
        if (predispitnaObavezaDTO.getId() != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        predispitnaObavezaService.save(predispitnaObavezaDTO);
        return new ResponseEntity<PredispitnaObavezaDTO>(predispitnaObavezaDTO, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'NASTAVNIK')")
    @PutMapping
    public ResponseEntity<PredispitnaObavezaDTO> updatePredispitnaObaveza(
            @RequestBody PredispitnaObavezaDTO predispitnaObavezaDTO) {
        if (predispitnaObavezaDTO.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        predispitnaObavezaService.update(predispitnaObavezaDTO);
        return new ResponseEntity<PredispitnaObavezaDTO>(predispitnaObavezaDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'NASTAVNIK')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePredispitnaObaveza(@PathVariable Long id) {
        PredispitnaObavezaDTO predispitnaObaveza = predispitnaObavezaService.findOne(id);
        if (predispitnaObaveza.getId() != null) {
            predispitnaObavezaService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
