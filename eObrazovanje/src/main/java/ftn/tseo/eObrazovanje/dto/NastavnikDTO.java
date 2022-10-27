package ftn.tseo.eObrazovanje.dto;

import java.util.HashSet;
import java.util.Set;

import ftn.tseo.eObrazovanje.model.Nastavnik;
import ftn.tseo.eObrazovanje.model.Nastavnik.TipNastavnika;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NastavnikDTO extends KorisnikDTO {
    private String katedra;
	private TipNastavnika uloga;
    private Set<PredmetDTO> predaje = new HashSet<PredmetDTO>();

    public NastavnikDTO(String katedra, TipNastavnika uloga, Set<PredmetDTO> predaje) {
        super();
        this.katedra = katedra;
        this.uloga = uloga;
        this.predaje = predaje;
    }

    public NastavnikDTO(Nastavnik nastavnik) {
        super(nastavnik);
        this.katedra = nastavnik.getKatedra();
        this.uloga = nastavnik.getUloga();
    }
}
