package ftn.tseo.eObrazovanje.service;

import java.util.Date;
import java.util.List;

import ftn.tseo.eObrazovanje.dto.NastavnikDTO;
import ftn.tseo.eObrazovanje.dto.PohadjanjePredmetaDTO;
import ftn.tseo.eObrazovanje.dto.StudentDTO;

public interface PohadjanjePredmetaServiceInterface {
    
    List<PohadjanjePredmetaDTO> findAll();
    PohadjanjePredmetaDTO findOne(Long id);
    void save(PohadjanjePredmetaDTO predmet);
    void update(PohadjanjePredmetaDTO pohadjanjePredmetaDTO);
    void remove(Long id);


    List<PohadjanjePredmetaDTO> findPohadjanjeZaPredmet(Long id);
    List<PohadjanjePredmetaDTO> findForStudenta(String korisnickoIme);
    List<StudentDTO> findStudenteZaPohadjanjePredmeta(Long id);
    // List<StudentDTO> findStudenteZaPredmet(Long id);
    List<PohadjanjePredmetaDTO> findForNastavnika(Long id);
    List<NastavnikDTO> findPredavace(Long id);
    List<PohadjanjePredmetaDTO> findByStartDate(Date from, Date to);
    List<PohadjanjePredmetaDTO> findByEndDate(Date endDate);
    List<PohadjanjePredmetaDTO> findPohadjanjePredmetaStudenta(Long id, String korisnickoIme);
}
