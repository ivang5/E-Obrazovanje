package ftn.tseo.eObrazovanje.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import ftn.tseo.eObrazovanje.model.PohadjanjePredmeta;
import ftn.tseo.eObrazovanje.model.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PohadjanjePredmetaDTO {
    
    private Long id;
    private Date startDate;
    private Date endDate;
    private Set<StudentDTO> studenti = new HashSet<>();
    private PredmetDTO predmet;
    
    public PohadjanjePredmetaDTO(PohadjanjePredmeta p){
        this.id = p.getId();
        this.startDate = p.getStartDate();
        this.endDate = p.getEndDate();
        this.predmet = new PredmetDTO(p.getPredmet());
        Set<StudentDTO> studenti = new HashSet<>();
        for(Student s : p.getStudenti()){
            studenti.add(new StudentDTO(s));
        }
        this.studenti = studenti;
    }
}
