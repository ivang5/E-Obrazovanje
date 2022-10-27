package ftn.tseo.eObrazovanje.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("STUDENT")
@Getter
@Setter
@NoArgsConstructor
@SuppressWarnings("unused")
public class Student extends Korisnik{

	private String jmbg;
	private String smer;
	private int godinaStudija;
	private double racun;
	@ManyToMany(mappedBy = "studenti")
	private Set<PohadjanjePredmeta> pohadjaPredmete = new HashSet<PohadjanjePredmeta>();
	
	public Student(String jmbg, String smer, int godinaStudija, double racun, Set<PohadjanjePredmeta> pohadjaPredmete) {
		super();
		this.jmbg = jmbg;
		this.smer = smer;
		this.godinaStudija = godinaStudija;
		this.racun = racun;
		this.pohadjaPredmete = pohadjaPredmete;
	}

	public Student(Long id, String ime, String prezime, String korisnickoIme, String lozinka, String email,
			String adresa, String grad, String brojTelefona, String jmbg, String smer, int godinaStudija,
			double racun) {
		super(id, ime, prezime, korisnickoIme, lozinka, email, adresa, grad, brojTelefona);
		this.jmbg = jmbg;
		this.smer = smer;
		this.godinaStudija = godinaStudija;
		this.racun = racun;
	}

	
	
}
