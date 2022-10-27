package ftn.tseo.eObrazovanje.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.tseo.eObrazovanje.dto.NastavnikDTO;
import ftn.tseo.eObrazovanje.dto.PredmetDTO;
import ftn.tseo.eObrazovanje.model.Nastavnik;
import ftn.tseo.eObrazovanje.model.Predmet;
import ftn.tseo.eObrazovanje.repository.NastavnikRepository;
import ftn.tseo.eObrazovanje.repository.PredmetRepository;

@Service
public class PredmetService implements PredmetServiceInterface{
    
    @Autowired
    private PredmetRepository predmetRepository;

    @Autowired
    private NastavnikRepository nastavnikRepository;


    @Override
    public List<PredmetDTO> findAll(){
        List<Predmet> predmeti = predmetRepository.findAll();
        List<PredmetDTO> predmetiDTO = new ArrayList<>();
        for(Predmet p : predmeti){
            PredmetDTO pDto = new PredmetDTO(p);
            predmetiDTO.add(pDto);
        }

        return predmetiDTO;
    }

    @Override
    public PredmetDTO findOne(Long id){
        Predmet p = predmetRepository.findById(id).orElse(null);
        return new PredmetDTO(p);
    }

    @Override
    public List<PredmetDTO> findForNastavnik(String korisnickoIme) {
        List<Predmet> predmeti = predmetRepository.findByPredavac(korisnickoIme);
        List<PredmetDTO> predmetiDTO = new ArrayList<>();
        for(Predmet p : predmeti){
            PredmetDTO pDto = new PredmetDTO(p);
            predmetiDTO.add(pDto);
        }
        return predmetiDTO;
    }
    // public List<Predmet> findByPredavac()
    @Override
    public PredmetDTO save(PredmetDTO predmet){
        Predmet novi = predmetRepository.save(populatePredmet(predmet, new Predmet()));
        return new PredmetDTO(novi);
    }

    @Override
    public PredmetDTO update(PredmetDTO predmet) {
        Predmet p = predmetRepository.findById(predmet.getId()).orElse(null);
        if(p != null){
            Predmet novi = populatePredmet(predmet, p);
            predmetRepository.save(novi);
            return new PredmetDTO(novi);
        }
        return null;
    }
    
    @Override
    public void remove(Long id){
        predmetRepository.deleteById(id);
    }

    private Predmet populatePredmet(PredmetDTO predmetDTO, Predmet predmet){
        if(predmetDTO.getESPB() != 0){
            predmet.setESPB(predmetDTO.getESPB());
        }
        if(predmetDTO.getNaziv() != null) {
            predmet.setNaziv(predmetDTO.getNaziv());
        }
        List<Nastavnik> predavaci = new ArrayList<>();
        if(predmetDTO.getPredavaci() != null){

            for(NastavnikDTO n : predmetDTO.getPredavaci()){
                Nastavnik nastavnik = nastavnikRepository.findByKorisnickoIme(n.getKorisnickoIme());
                predavaci.add(nastavnik);
            }
            predmet.setPredavaci(new HashSet<>(predavaci));
        }
        return predmet;
    }

   


    
}
