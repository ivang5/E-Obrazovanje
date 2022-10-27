package ftn.tseo.eObrazovanje.dto;

import ftn.tseo.eObrazovanje.model.Dokument;
import ftn.tseo.eObrazovanje.model.Dokument.TipDokumenta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DokumentDTO {

    private Long id;
    private String nazivDokumenta;
    private TipDokumenta tipDokumenta;
    private StudentDTO student;

    public DokumentDTO(Dokument d) {
        this.id = d.getId();
        this.nazivDokumenta = d.getNaziv();
        this.tipDokumenta = d.getTipDokumenta();
        this.student = new StudentDTO(d.getStudent());

    }

}
