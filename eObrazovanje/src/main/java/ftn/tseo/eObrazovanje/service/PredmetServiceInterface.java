package ftn.tseo.eObrazovanje.service;

import java.util.List;

import ftn.tseo.eObrazovanje.dto.PredmetDTO;

public interface PredmetServiceInterface {
    public PredmetDTO findOne(Long id);
    public List<PredmetDTO> findAll();
    public PredmetDTO save(PredmetDTO predmet);
    public PredmetDTO update(PredmetDTO predmet);
    public void remove(Long id);
    public List<PredmetDTO> findForNastavnik(String korisnickoIme);
}
