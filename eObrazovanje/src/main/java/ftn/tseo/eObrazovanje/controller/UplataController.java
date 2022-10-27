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

import ftn.tseo.eObrazovanje.dto.UplataDTO;
import ftn.tseo.eObrazovanje.service.UplataServiceInterface;
import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequestMapping(value = "uplata")
@RequiredArgsConstructor
public class UplataController {

    @Autowired
    private UplataServiceInterface uplataService;

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'STUDENT')")
    @GetMapping
    public ResponseEntity<List<UplataDTO>> getUplate() {
        List<UplataDTO> dokumenti = uplataService.findAll();
        return new ResponseEntity<List<UplataDTO>>(dokumenti, HttpStatus.OK);

    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'STUDENT')")
    @GetMapping("/{id}")
    public ResponseEntity<UplataDTO> getUplata(@PathVariable Long id) {
        UplataDTO uplata = uplataService.findOne(id);
        if (uplata == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(uplata, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'STUDENT')")
    @GetMapping(value = "/student/{id}")
    public ResponseEntity<List<UplataDTO>> getUplataByStudent(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(uplataService.findByStudent(id));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'STUDENT')")
    @PostMapping
    public ResponseEntity<UplataDTO> saveUplata(@Valid @RequestBody UplataDTO uplataDTO) {
        if (uplataDTO.getId() != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        uplataService.save(uplataDTO);
        return new ResponseEntity<UplataDTO>(uplataDTO, HttpStatus.CREATED);

    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PutMapping
    public ResponseEntity<UplataDTO> updateUplata(@Valid @RequestBody UplataDTO uplataDTO) {
        if (uplataDTO.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        uplataService.update(uplataDTO);
        return new ResponseEntity<UplataDTO>(uplataDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUplata(@PathVariable Long id) {
        UplataDTO uplata = uplataService.findOne(id);
        if (uplata.getId() != null) {
            uplataService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
