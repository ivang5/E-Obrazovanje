package ftn.tseo.eObrazovanje.service;

import java.util.List;

import ftn.tseo.eObrazovanje.dto.PredispitnaObavezaDTO;

public interface PredispitnaObavezaServiceInterface {

    public PredispitnaObavezaDTO findOne(Long id);

    public List<PredispitnaObavezaDTO> findAll();

    public List<PredispitnaObavezaDTO> findPrijavljenePredispitneObaveze(Long id);

    public List<PredispitnaObavezaDTO> findNeprijavljenePredispitneObaveze(Long id);

    public void save(PredispitnaObavezaDTO predispitnaObaveza);

    public void update(PredispitnaObavezaDTO predispitnaObaveza);

    public void remove(Long id);

}
