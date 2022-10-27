package ftn.tseo.eObrazovanje.service;

import java.util.List;

import ftn.tseo.eObrazovanje.dto.NastavnikDTO;
import ftn.tseo.eObrazovanje.dto.PredmetDTO;
import ftn.tseo.eObrazovanje.model.Nastavnik;

public interface NastavnikServiceInterface {

    public Nastavnik findOne(Long id);
    public Nastavnik findByKorisnickoIme(String username);
    public List<NastavnikDTO> findAll();
    public void save(NastavnikDTO nastavnik);
    public void update(NastavnikDTO nastavnik);
    public void remove(Long id);
    public List<PredmetDTO> findPredaje(NastavnikDTO dto);
}
