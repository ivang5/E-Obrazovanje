package ftn.tseo.eObrazovanje.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("NASTAVNIK")
@Getter 
@Setter
@NoArgsConstructor
@SuppressWarnings("unused")
public class Nastavnik extends Korisnik {

	public enum TipNastavnika{
		PROFESOR,
		ASISTENT,
		DEMONSTRATOR
	}; 
	
	private String katedra;
	private TipNastavnika uloga;
	@ManyToMany(mappedBy = "predavaci")
	private Set<Predmet> predaje = new HashSet<Predmet>();

	public Nastavnik(String katedra, TipNastavnika uloga, Set<Predmet> predaje) {
		super();
		this.katedra = katedra;
		this.uloga = uloga;
		this.predaje = predaje;
	}

	public Nastavnik(Long id, String ime, String prezime, String korisnickoIme, String lozinka, String email, String adresa, 
				String grad, String brojTelefona, String katedra, TipNastavnika uloga, Set<Predmet> predaje) {
		super(id, ime, prezime, korisnickoIme, lozinka, email, adresa, grad, brojTelefona);
		this.katedra = katedra;
		this.uloga = uloga;
		this.predaje = predaje;
	}
	public Nastavnik(Long id, String ime, String prezime, String korisnickoIme, String lozinka, String email, String adresa, 
			String grad, String brojTelefona, String katedra, TipNastavnika uloga) {
		super(id, ime, prezime, korisnickoIme, lozinka, email, adresa, grad, brojTelefona);
		this.katedra = katedra;
		this.uloga = uloga;
	}
}
