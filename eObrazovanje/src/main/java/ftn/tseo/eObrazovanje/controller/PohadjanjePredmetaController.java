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

import ftn.tseo.eObrazovanje.dto.NastavnikDTO;
import ftn.tseo.eObrazovanje.dto.PohadjanjePredmetaDTO;
import ftn.tseo.eObrazovanje.dto.StudentDTO;
import ftn.tseo.eObrazovanje.service.PohadjanjePredmetaServiceInterface;
import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RestController
@RequestMapping("pohadjanjePredmeta")
@RequiredArgsConstructor
public class PohadjanjePredmetaController {
    
    @Autowired
    private PohadjanjePredmetaServiceInterface pohadjanjePredmetaService;

    // private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping("/")
    public ResponseEntity<List<PohadjanjePredmetaDTO>> findAll(){
        List<PohadjanjePredmetaDTO> pohadjanja = pohadjanjePredmetaService.findAll();
        return new ResponseEntity<List<PohadjanjePredmetaDTO>>(pohadjanja, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PohadjanjePredmetaDTO> findOne(@PathVariable Long id){
        return new ResponseEntity<PohadjanjePredmetaDTO>(pohadjanjePredmetaService.findOne(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PostMapping
    public ResponseEntity<PohadjanjePredmetaDTO> save(@RequestBody PohadjanjePredmetaDTO dto){
        if(dto.getId() != null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        pohadjanjePredmetaService.save(dto);
        return new ResponseEntity<PohadjanjePredmetaDTO>(HttpStatus.CREATED);  
    }
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PutMapping
    public ResponseEntity<PohadjanjePredmetaDTO> update(@RequestBody PohadjanjePredmetaDTO dto){
        if(dto.getId() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        pohadjanjePredmetaService.update(dto);
        return new ResponseEntity<PohadjanjePredmetaDTO>(dto, HttpStatus.OK);  
    }
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        PohadjanjePredmetaDTO pohadjanje = pohadjanjePredmetaService.findOne(id);
        if(pohadjanje != null){
            pohadjanjePredmetaService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }





    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'NASTAVNIK')")
    @GetMapping("/predmet/{id}")
    public ResponseEntity<List<PohadjanjePredmetaDTO>> findForPredmet(@PathVariable Long id){
        return new ResponseEntity<List<PohadjanjePredmetaDTO>>(pohadjanjePredmetaService.findPohadjanjeZaPredmet(id), HttpStatus.OK);
    }
    @GetMapping("/student/{korisnickoIme}")
    public ResponseEntity<List<PohadjanjePredmetaDTO>> findForStudent(@PathVariable String korisnickoIme){
        return new ResponseEntity<List<PohadjanjePredmetaDTO>>(pohadjanjePredmetaService.findForStudenta(korisnickoIme), HttpStatus.OK);
    }
    // @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/predmet/{id}/student/{korisnickoIme}")
    public ResponseEntity<List<PohadjanjePredmetaDTO>> findPohadjanjePredmetaStudenta(@PathVariable Long id, @PathVariable String korisnickoIme){
        return new ResponseEntity<List<PohadjanjePredmetaDTO>>(pohadjanjePredmetaService.findPohadjanjePredmetaStudenta(id, korisnickoIme), HttpStatus.OK);
    }
    @GetMapping("/{id}/studenti")
    public ResponseEntity<List<StudentDTO>> findStudenteZaPohadjanje(@PathVariable Long id){
        return new ResponseEntity<List<StudentDTO>>(pohadjanjePredmetaService.findStudenteZaPohadjanjePredmeta(id), HttpStatus.OK);
    }
    @GetMapping("/nastavnik/{id}")
    public ResponseEntity<List<PohadjanjePredmetaDTO>> findForNastavnik(@PathVariable Long id){
        return new ResponseEntity<List<PohadjanjePredmetaDTO>>(pohadjanjePredmetaService.findForNastavnika(id), HttpStatus.OK);
    }
    @GetMapping("/{id}/nastavnici")
    public ResponseEntity<List<NastavnikDTO>> findPredavace(@PathVariable Long id){
        return new ResponseEntity<List<NastavnikDTO>>(pohadjanjePredmetaService.findPredavace(id), HttpStatus.OK);
    }
    //startdate
    // @GetMapping("/{from}/{to}")
    // public ResponseEntity<List<PohadjanjePredmetaDTO>> findStartDate(@PathVariable Date from, @PathVariable Date to){
        
    //     return new ResponseEntity<List<PohadjanjePredmetaDTO>>(pohadjanjePredmetaService.findByStartDate(), HttpStatus.OK);
    // }
    //enddate
    







    
    
}
