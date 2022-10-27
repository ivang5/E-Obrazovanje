package ftn.tseo.eObrazovanje.service;

import java.util.List;

import ftn.tseo.eObrazovanje.dto.DokumentDTO;

public interface DokumentServiceInterface {

    public DokumentDTO findOne(Long id);

    public List<DokumentDTO> findAll();

    public List<DokumentDTO> findByStudent(Long id);

    public void save(DokumentDTO dokument);

    public void update(DokumentDTO dokument);

    public void remove(Long id);

}
