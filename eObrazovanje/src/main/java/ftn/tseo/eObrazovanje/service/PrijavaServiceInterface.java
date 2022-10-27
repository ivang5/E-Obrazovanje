package ftn.tseo.eObrazovanje.service;

import java.util.List;

import ftn.tseo.eObrazovanje.dto.PrijavaDTO;

public interface PrijavaServiceInterface {
    public List<PrijavaDTO> findAll();
    public List<PrijavaDTO> findAllByStudent(Long id);
    public List<PrijavaDTO> findAllByNastavnik(Long id);
    public PrijavaDTO findOne(Long id);
    public PrijavaDTO save(PrijavaDTO prijava);
    public PrijavaDTO update(PrijavaDTO prijava);
    public void remove(Long id);
}
