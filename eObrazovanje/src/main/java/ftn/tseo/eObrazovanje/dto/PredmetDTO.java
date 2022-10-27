package ftn.tseo.eObrazovanje.dto;

import java.util.HashSet;
import java.util.Set;

import ftn.tseo.eObrazovanje.model.Nastavnik;
import ftn.tseo.eObrazovanje.model.Predmet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PredmetDTO {
    
    private Long id;
    private String naziv;
    private int ESPB;
    private Set<NastavnikDTO> predavaci = new HashSet<>();
    
    public PredmetDTO(Predmet p){
        this.id = p.getId();
        this.naziv = p.getNaziv();
        this.ESPB = p.getESPB();
        Set<NastavnikDTO> predavaci = new HashSet<>();
        for (Nastavnik n : p.getPredavaci()) {
            NastavnikDTO predavac = new NastavnikDTO(n);
            predavaci.add(predavac);
        }
        this.predavaci = predavaci;
    }
}