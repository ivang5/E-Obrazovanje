package ftn.tseo.eObrazovanje.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.tseo.eObrazovanje.dto.PredispitnaObavezaDTO;
import ftn.tseo.eObrazovanje.model.PredispitnaObaveza;
import ftn.tseo.eObrazovanje.repository.PredispitnaObavezaRepository;
import ftn.tseo.eObrazovanje.repository.PredmetRepository;
import ftn.tseo.eObrazovanje.repository.StudentRepository;

@Service
@Transactional
@SuppressWarnings("unused")
public class PredispitnaObavezaService implements PredispitnaObavezaServiceInterface {

    @Autowired
    private PredispitnaObavezaRepository predispitnaObavezaRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PredmetRepository predmetRepository;

    @Override
    public PredispitnaObavezaDTO findOne(Long id) {
        return new PredispitnaObavezaDTO(predispitnaObavezaRepository.findById(id).orElse(null));
    }

    @Override
    public List<PredispitnaObavezaDTO> findAll() {
        List<PredispitnaObavezaDTO> predispitnaObavezaDTO = new ArrayList<PredispitnaObavezaDTO>();

        for (PredispitnaObaveza p : predispitnaObavezaRepository.findAll()) {
            predispitnaObavezaDTO.add(new PredispitnaObavezaDTO(p));
        }

        return predispitnaObavezaDTO;
    }

    @Override
    public List<PredispitnaObavezaDTO> findPrijavljenePredispitneObaveze(Long id) {
        List<PredispitnaObavezaDTO> predispitnaObavezaDTO = new ArrayList<PredispitnaObavezaDTO>();

        for (PredispitnaObaveza p : predispitnaObavezaRepository.findPrijavljene(id)) {
            predispitnaObavezaDTO.add(new PredispitnaObavezaDTO(p));
        }

        return predispitnaObavezaDTO;
    }

    @Override
    public List<PredispitnaObavezaDTO> findNeprijavljenePredispitneObaveze(Long id) {
        List<PredispitnaObavezaDTO> predispitnaObavezaDTO = new ArrayList<PredispitnaObavezaDTO>();

        for (PredispitnaObaveza p : predispitnaObavezaRepository.findNeprijavljene(id)) {
            predispitnaObavezaDTO.add(new PredispitnaObavezaDTO(p));
        }

        return predispitnaObavezaDTO;
    }


    @Override
    public void save(PredispitnaObavezaDTO predispitnaObavezaDTO) {
        PredispitnaObaveza predispitnaObaveza = new PredispitnaObaveza();
        populatePredispitnaObaveza(predispitnaObaveza, predispitnaObavezaDTO);
    }

    @Override
    public void update(PredispitnaObavezaDTO predispitnaObavezaDTO) {
        PredispitnaObaveza predispitnaObaveza = predispitnaObavezaRepository.getById(predispitnaObavezaDTO.getId());

        if (predispitnaObaveza != null) {
            populatePredispitnaObaveza(predispitnaObaveza, predispitnaObavezaDTO);
        }
    }

    @Override
    public void remove(Long id) {
        predispitnaObavezaRepository.deleteById(id);
    }

    private void populatePredispitnaObaveza(PredispitnaObaveza predispitnaObaveza,
            PredispitnaObavezaDTO predispitnaObavezaDTO) {

        predispitnaObaveza.setTipTesta(predispitnaObavezaDTO.getTipPredispitneObaveze());
        predispitnaObaveza.setPredmet(predmetRepository.getById(predispitnaObavezaDTO.getPredmet().getId()));
        predispitnaObaveza.setDatum(predispitnaObavezaDTO.getDatum());
        predispitnaObaveza.setSala(predispitnaObavezaDTO.getSala());

        predispitnaObavezaRepository.save(predispitnaObaveza);
    }
}
