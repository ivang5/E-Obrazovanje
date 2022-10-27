package ftn.tseo.eObrazovanje.controller;

import java.util.ArrayList;
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

import ftn.tseo.eObrazovanje.dto.PohadjanjePredmetaDTO;
import ftn.tseo.eObrazovanje.dto.PredmetDTO;
import ftn.tseo.eObrazovanje.service.PohadjanjePredmetaServiceInterface;
import ftn.tseo.eObrazovanje.service.PredmetServiceInterface;
import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RestController
@RequestMapping("predmeti")
@RequiredArgsConstructor
public class PredmetController {
    @Autowired
    private PredmetServiceInterface predmetService;
    @Autowired
    private PohadjanjePredmetaServiceInterface pohadjanjePredmetaService;

    @GetMapping
    public ResponseEntity<List<PredmetDTO>> getPredmete() {
		List<PredmetDTO> predmeti = predmetService.findAll();
		return new ResponseEntity<>(predmeti, HttpStatus.OK);
	}

    @GetMapping("/{id}")
    public ResponseEntity<PredmetDTO> getPredmet(@PathVariable("id") Long id) {
		PredmetDTO predmet = predmetService.findOne(id);
        if(predmet == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(predmet, HttpStatus.OK);
	}
    
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PostMapping
    public ResponseEntity<PredmetDTO> save(@RequestBody PredmetDTO predmetDTO){
        if(predmetDTO.getId() != null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        PredmetDTO novi = predmetService.save(predmetDTO);
        return new ResponseEntity<PredmetDTO>(novi, HttpStatus.CREATED);        
    }
   
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PutMapping
    public ResponseEntity<PredmetDTO> update(@RequestBody PredmetDTO predmetDTO) {
        PredmetDTO predmet = new PredmetDTO();

        try {
            predmet = predmetService.update(predmetDTO);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        return new ResponseEntity<PredmetDTO>(predmet, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        PredmetDTO predmet = predmetService.findOne(id);
        if(predmet != null){
            predmetService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }





    @GetMapping("/student/{korisnickoIme}")
    public ResponseEntity<List<PredmetDTO>> findForStudent(@PathVariable String korisnickoIme){
        List<PohadjanjePredmetaDTO> pohadjanja = pohadjanjePredmetaService.findForStudenta(korisnickoIme);
        List<PredmetDTO> predmeti = new ArrayList<>();
        for(PohadjanjePredmetaDTO p : pohadjanja){
            predmeti.add(p.getPredmet());
        }
        return new ResponseEntity<List<PredmetDTO>>(predmeti, HttpStatus.OK);
    }


    @GetMapping("/nastavnik/{korisnickoIme}")
    public ResponseEntity<List<PredmetDTO>> findForNastavnik(@PathVariable String korisnickoIme){
        System.out.println(korisnickoIme);
        List<PredmetDTO> predmeti = predmetService.findForNastavnik(korisnickoIme);
       
        return new ResponseEntity<List<PredmetDTO>>(predmeti, HttpStatus.OK);
    }
}
