package ftn.tseo.eObrazovanje.dto;

import ftn.tseo.eObrazovanje.model.Korisnik;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class KorisnikDTO {
    private Long id;
	private String ime;
	private String prezime;
	private String korisnickoIme;
	private String lozinka;
	private String email;
	private String adresa;
	private String grad;
	private String brojTelefona;

    public KorisnikDTO(Long id, String ime, String prezime, String korisnickoIme, String lozinka, String email,
            String adresa, String grad, String brojTelefona) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
        this.email = email;
        this.adresa = adresa;
        this.grad = grad;
        this.brojTelefona = brojTelefona;
    }

    public KorisnikDTO(Long id, String korisnickoIme, String lozinka) {
        this.id = id;
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
    }

    public KorisnikDTO(Korisnik korisnik) {
        this.id = korisnik.getId();
        this.ime = korisnik.getIme();
        this.prezime = korisnik.getPrezime();
        this.korisnickoIme = korisnik.getKorisnickoIme();
        this.lozinka = korisnik.getLozinka();
        this.email = korisnik.getEmail();
        this.adresa = korisnik.getAdresa();
        this.grad = korisnik.getGrad();
        this.brojTelefona = korisnik.getBrojTelefona();
    }
}
