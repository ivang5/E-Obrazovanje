package ftn.tseo.eObrazovanje.dto;

import ftn.tseo.eObrazovanje.model.Prijava;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrijavaDTO {
    
    private Long id;
    private boolean ocenjen;
    private boolean polozen;
	private int bodovi;
    private PredispitnaObavezaDTO predispitnaObaveza;
    private StudentDTO student;

    public PrijavaDTO(Prijava p) {
        this.id = p.getId();
        this.ocenjen = p.isOcenjen();
        this.polozen = p.isPolozen();
        this.bodovi = p.getBodovi();
        this.predispitnaObaveza = new PredispitnaObavezaDTO(p.getPredispitnaObaveza());
        this.student = new StudentDTO(p.getStudent());
    }
}
