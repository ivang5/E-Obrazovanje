package ftn.tseo.eObrazovanje.dto;

import java.time.LocalDate;

import ftn.tseo.eObrazovanje.model.PredispitnaObaveza;
import ftn.tseo.eObrazovanje.model.PredispitnaObaveza.TipTesta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PredispitnaObavezaDTO {

    private Long id;
    private TipTesta tipPredispitneObaveze;
    private PredmetDTO predmet;
    private LocalDate datum;
    private String sala;

    public PredispitnaObavezaDTO(PredispitnaObaveza p) {
        this.id = p.getId();
        this.tipPredispitneObaveze = p.getTipTesta();
        this.predmet = new PredmetDTO(p.getPredmet());
        this.datum = p.getDatum();
        this.sala = p.getSala();
    }

}
