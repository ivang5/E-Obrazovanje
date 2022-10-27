package ftn.tseo.eObrazovanje.dto;

import java.util.Date;

import javax.validation.constraints.Min;

import ftn.tseo.eObrazovanje.model.Uplata;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UplataDTO {

    private Long id;
    private String svrha;
    @Min(value = 200, message = "minimum")
    private Double iznos;
    private Date datum;
    private StudentDTO student;

    public UplataDTO(Uplata u) {
        this.id = u.getId();
        this.svrha = u.getSvrha();
        this.iznos = u.getIznos();
        this.datum = u.getDatum();
        this.student = new StudentDTO(u.getStudent());
    }
}