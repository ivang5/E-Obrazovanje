package ftn.tseo.eObrazovanje.controller;

import java.util.List;

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

import ftn.tseo.eObrazovanje.dto.DokumentDTO;
import ftn.tseo.eObrazovanje.model.Korisnik;
import ftn.tseo.eObrazovanje.service.DokumentServiceInterface;
import ftn.tseo.eObrazovanje.service.KorisnikServiceInterface;
import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequestMapping(value = "dokument")
@RequiredArgsConstructor
public class DokumentController {

    @Autowired
    private DokumentServiceInterface dokumentService;
    @Autowired
    private KorisnikServiceInterface korisnikService;

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'STUDENT')")
    @GetMapping
    public ResponseEntity<List<DokumentDTO>> getDokuments() {
        List<DokumentDTO> dokumenti = dokumentService.findAll();
        return new ResponseEntity<List<DokumentDTO>>(dokumenti, HttpStatus.OK);

    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'STUDENT')")
    @GetMapping("/{id}")
    public ResponseEntity<DokumentDTO> getDokument(@PathVariable Long id) {
        DokumentDTO dokument = dokumentService.findOne(id);
        if (dokument == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dokument, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PostMapping
    public ResponseEntity<DokumentDTO> saveDokument(@RequestBody DokumentDTO dokumentDTO) {
        if (dokumentDTO.getId() != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        dokumentService.save(dokumentDTO);
        return new ResponseEntity<DokumentDTO>(HttpStatus.CREATED);

    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PutMapping
    public ResponseEntity<DokumentDTO> updateDokument(@RequestBody DokumentDTO dokumentDTO) {
        if (dokumentDTO.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        dokumentService.update(dokumentDTO);
        return new ResponseEntity<DokumentDTO>(dokumentDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDokument(@PathVariable Long id) {
        DokumentDTO dokument = dokumentService.findOne(id);
        if (dokument != null) {
            dokumentService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'STUDENT')")
    @GetMapping(value = "/student/{korisnickoIme}")
    public ResponseEntity<List<DokumentDTO>> getDokumentByStudent(@PathVariable("korisnickoIme") String korisnickoIme) {
        Korisnik student = korisnikService.findByKorisnickoIme(korisnickoIme);
        return ResponseEntity.ok().body(dokumentService.findByStudent(student.getId()));
    }

}
