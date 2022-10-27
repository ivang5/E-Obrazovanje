package ftn.tseo.eObrazovanje.service;

import java.util.List;

import ftn.tseo.eObrazovanje.dto.KorisnikDTO;
import ftn.tseo.eObrazovanje.model.Korisnik;

public interface KorisnikServiceInterface {
    public Korisnik findOne(Long id);
    public Korisnik findByKorisnickoIme(String username);
    public List<KorisnikDTO> findAll();
    public void save(KorisnikDTO korisnik);
    public void update(KorisnikDTO korisnik);
    public void remove(Long id);
    public String getRole(Long id);
}
