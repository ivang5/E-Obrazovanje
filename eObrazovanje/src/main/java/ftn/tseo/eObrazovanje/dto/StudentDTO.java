package ftn.tseo.eObrazovanje.dto;

import java.util.HashSet;
import java.util.Set;

import ftn.tseo.eObrazovanje.model.Student;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudentDTO extends KorisnikDTO {
    
    private String jmbg;
	private String smer;
	private int godinaStudija;
    private double racun;
    private Set<PohadjanjePredmetaDTO> pohadjaPredmete = new HashSet<PohadjanjePredmetaDTO>();

    public StudentDTO(String jmbg, String smer, int godinaStudija, double racun, Set<PohadjanjePredmetaDTO> pohadjaPredmete) {
        super();
        this.jmbg = jmbg;
        this.smer = smer;
        this.godinaStudija = godinaStudija;
        this.racun = racun;
        this.pohadjaPredmete = pohadjaPredmete;
    }

    public StudentDTO(Student student) {
        super(student);
        this.jmbg = student.getJmbg();
        this.smer = student.getSmer();
        this.godinaStudija = student.getGodinaStudija();
        this.racun = student.getRacun();
    }
}
