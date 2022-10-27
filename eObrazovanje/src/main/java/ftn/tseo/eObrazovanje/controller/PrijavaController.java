package ftn.tseo.eObrazovanje.controller;

import java.util.List;

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

import ftn.tseo.eObrazovanje.dto.PrijavaDTO;
import ftn.tseo.eObrazovanje.service.PrijavaServiceInterface;
import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "prijave")
@RequiredArgsConstructor
public class PrijavaController {
    
    private final PrijavaServiceInterface prijavaService;

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping
    public ResponseEntity<List<PrijavaDTO>> getAllPrijave() {
        return ResponseEntity.ok().body(prijavaService.findAll());
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<PrijavaDTO> getPrijavaById(@PathVariable("id") Long id) {
        PrijavaDTO prijava = prijavaService.findOne(id);

        if (prijava == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(prijava);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'STUDENT')")
    @GetMapping(value = "/student/{id}")
    public ResponseEntity<List<PrijavaDTO>> getPrijaveByStudent(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(prijavaService.findAllByStudent(id));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'NASTAVNIK')")
    @GetMapping(value = "/nastavnik/{id}")
    public ResponseEntity<List<PrijavaDTO>> getPrijaveByNastavnik(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(prijavaService.findAllByNastavnik(id));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'STUDENT')")
    @PostMapping()
    public ResponseEntity<?> createPrijava(@RequestBody PrijavaDTO prijava) {
        try {
            prijavaService.save(prijava);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Bad request, something went wrong");
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'NASTAVNIK')")
    @PutMapping()
    public ResponseEntity<?> updatePrijava(@RequestBody PrijavaDTO prijava) {
        try {
            prijavaService.update(prijava);
        } catch (Exception e) {
            return new ResponseEntity<String>("Bad request, something went wrong", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletePrijava(@PathVariable Long id) {
        PrijavaDTO prijava = prijavaService.findOne(id);

        if (prijava != null) {
            prijavaService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
