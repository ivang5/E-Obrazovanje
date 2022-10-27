package ftn.tseo.eObrazovanje.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ftn.tseo.eObrazovanje.dto.PrijavaDTO;
import ftn.tseo.eObrazovanje.model.Prijava;
import ftn.tseo.eObrazovanje.repository.PredispitnaObavezaRepository;
import ftn.tseo.eObrazovanje.repository.PrijavaRepository;
import ftn.tseo.eObrazovanje.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class PrijavaService implements PrijavaServiceInterface {

    private final PrijavaRepository prijavaRepository;
    private final PredispitnaObavezaRepository predispitnaObavezaRepository;
    private final StudentRepository studentRepository;

    @Override
    public List<PrijavaDTO> findAll() {
        log.info("Pronalazenje svih prijava");
        List<Prijava> prijave = prijavaRepository.findAll();
        List<PrijavaDTO> prijaveDTO = new ArrayList<>();
        for(Prijava p : prijave){
            PrijavaDTO pDto = new PrijavaDTO(p);
            prijaveDTO.add(pDto);
        }

        return prijaveDTO;
    }

    @Override
    public List<PrijavaDTO> findAllByStudent(Long studentId) {
        log.info("Pronalazenje svih prijava za studenta {}", studentId);
        List<Prijava> prijave = prijavaRepository.findByStudent(studentId);
        List<PrijavaDTO> prijaveDTO = new ArrayList<>();
        for(Prijava p : prijave){
            PrijavaDTO pDto = new PrijavaDTO(p);
            prijaveDTO.add(pDto);
        }

        return prijaveDTO;
    }

    @Override
    public List<PrijavaDTO> findAllByNastavnik(Long nastavnikId) {
        log.info("Pronalazenje svih prijava za nastavnika {}", nastavnikId);
        List<Prijava> prijave = prijavaRepository.findByNastavnik(nastavnikId);
        List<PrijavaDTO> prijaveDTO = new ArrayList<>();
        for(Prijava p : prijave){
            PrijavaDTO pDto = new PrijavaDTO(p);
            prijaveDTO.add(pDto);
        }

        return prijaveDTO;
    }

    @Override
    public PrijavaDTO findOne(Long id) {
        log.info("Pronalazenje prijave {}", id);
        Prijava p = prijavaRepository.findById(id).orElse(null);
        return new PrijavaDTO(p);
    }

    @Override
    public PrijavaDTO save(PrijavaDTO prijavaDTO) {
        log.info("Cuvanje nove prijave {} u bazi", prijavaDTO.getId());
        Prijava prijava = prijavaRepository.save(populatePrijava(prijavaDTO, new Prijava()));
        return new PrijavaDTO(prijava);
    }

    @Override
    public PrijavaDTO update(PrijavaDTO prijavaDTO) {
        log.info("Azuriranje prijave {}", prijavaDTO.getId());
        Prijava prijava = prijavaRepository.findById(prijavaDTO.getId()).orElse(null);
        if(prijava != null){
            Prijava p = populatePrijava(prijavaDTO, prijava);
            return new PrijavaDTO(p);
        }
        return null;
    }

    @Override
    public void remove(Long id) {
        log.info("Brisanje prijave {}", id);
        prijavaRepository.deleteById(id);
    }

    private Prijava populatePrijava(PrijavaDTO prijavaDTO, Prijava prijava){
        prijava.setOcenjen(prijavaDTO.isOcenjen());
        prijava.setPolozen(prijavaDTO.isPolozen());
        prijava.setBodovi(prijavaDTO.getBodovi());
        prijava.setPredispitnaObaveza(predispitnaObavezaRepository.findById(prijavaDTO.getPredispitnaObaveza().getId()).orElse(null));
        prijava.setStudent(studentRepository.findByKorisnickoIme(prijavaDTO.getStudent().getKorisnickoIme()));
        
        return prijavaRepository.save(prijava);
    }
    
}
