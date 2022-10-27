package ftn.tseo.eObrazovanje.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.tseo.eObrazovanje.dto.NastavnikDTO;
import ftn.tseo.eObrazovanje.dto.PohadjanjePredmetaDTO;
import ftn.tseo.eObrazovanje.dto.StudentDTO;
import ftn.tseo.eObrazovanje.model.PohadjanjePredmeta;
import ftn.tseo.eObrazovanje.model.Predmet;
import ftn.tseo.eObrazovanje.model.Student;
import ftn.tseo.eObrazovanje.repository.NastavnikRepository;
import ftn.tseo.eObrazovanje.repository.PohadjanjePredmetaRepository;
import ftn.tseo.eObrazovanje.repository.PredmetRepository;
import ftn.tseo.eObrazovanje.repository.StudentRepository;

@Service
public class PohadjanjePredmetaService implements PohadjanjePredmetaServiceInterface{
    
    @Autowired
    private PohadjanjePredmetaRepository pohadjanjePredmetaRepository;
    @Autowired
    private PredmetRepository predmetRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private NastavnikRepository nastavnikRepository;

    @Override
    public List<PohadjanjePredmetaDTO> findAll(){
        List<PohadjanjePredmeta> pohadjanja = pohadjanjePredmetaRepository.findAll();
        List<PohadjanjePredmetaDTO> dtos = new ArrayList<>();
        for(PohadjanjePredmeta pp : pohadjanja){
            dtos.add(new PohadjanjePredmetaDTO(pp));
        }
        return dtos;
    }

    
    @Override
    public PohadjanjePredmetaDTO findOne(Long id) {
        return new PohadjanjePredmetaDTO(pohadjanjePredmetaRepository.findById(id).orElse(null));
    }

    @Override
    public void save(PohadjanjePredmetaDTO pohadjanjePredmeta){
        PohadjanjePredmeta novo = populatePohadjanjePredmeta(pohadjanjePredmeta, new PohadjanjePredmeta());
        pohadjanjePredmetaRepository.save(novo);
    }

    @Override
    public void update(PohadjanjePredmetaDTO pohadjanjePredmetaDTO) {
        PohadjanjePredmeta pp = pohadjanjePredmetaRepository.findById(pohadjanjePredmetaDTO.getId()).orElse(null);
        if(pp != null){
            PohadjanjePredmeta novo = populatePohadjanjePredmeta(pohadjanjePredmetaDTO, pp);
            pohadjanjePredmetaRepository.save(novo);
        }
        
    }

    @Override
    public void remove(Long id){
        pohadjanjePredmetaRepository.deleteById(id);
    }

   
    @Override
    public List<PohadjanjePredmetaDTO> findPohadjanjeZaPredmet(Long id) {
        List<PohadjanjePredmetaDTO> dtos = new ArrayList<>();
        List<PohadjanjePredmeta> pohadjanja = pohadjanjePredmetaRepository.findPohadjanjeZaPredmet(id);
        for(PohadjanjePredmeta p : pohadjanja){
            dtos.add(new PohadjanjePredmetaDTO(p));
        }
        return dtos;
    }


    @Override
    public List<StudentDTO> findStudenteZaPohadjanjePredmeta(Long id) {
        List<StudentDTO> dtos = new ArrayList<>();
        List<Student> studenti = new ArrayList<>();
        List<Long> ids = pohadjanjePredmetaRepository.findStudenteZaPohadjanjePredmeta(id);
        for(Long i : ids){
            Student s = studentRepository.findById(i).orElse(null);
            if(s != null){
                studenti.add(s);
            }
        }
        for(Student s : studenti){
            dtos.add(new StudentDTO(s));
        }
        return dtos;
    }
    
    @Override
    public List<PohadjanjePredmetaDTO> findForStudenta(String korisnickoIme) {
        List<PohadjanjePredmetaDTO> dtos = new ArrayList<>();
        List<PohadjanjePredmeta> pohadjanja = pohadjanjePredmetaRepository.findPohadjanjaStudenta(korisnickoIme);
        for(PohadjanjePredmeta s : pohadjanja){
            dtos.add(new PohadjanjePredmetaDTO(s));
        }
        return dtos;
    }

    @Override
    public List<PohadjanjePredmetaDTO> findPohadjanjePredmetaStudenta(Long id, String korisnickoIme) {
        List<PohadjanjePredmetaDTO> dtos = new ArrayList<>();
        List<PohadjanjePredmeta> pohadjanja = pohadjanjePredmetaRepository.findPohadjanjePredmetaStudenta(id, korisnickoIme);
        for(PohadjanjePredmeta s : pohadjanja){
            dtos.add(new PohadjanjePredmetaDTO(s));
        }
        return dtos;
    }

    @Override
    public List<PohadjanjePredmetaDTO> findForNastavnika(Long id) {
        List<PohadjanjePredmeta> pohadjanja = pohadjanjePredmetaRepository.findForNastavnika(id);
        List<PohadjanjePredmetaDTO> dtos = new ArrayList<>();
        for(PohadjanjePredmeta p : pohadjanja){
            dtos.add(new PohadjanjePredmetaDTO(p));
        }
        return dtos;
    }

    @Override
    public List<NastavnikDTO> findPredavace(Long id) {
        List<NastavnikDTO> dtos = new ArrayList<>();
        List<Long> ids = pohadjanjePredmetaRepository.findPredavace(id);
        for(Long i : ids){
            NastavnikDTO nastavnik = new NastavnikDTO(nastavnikRepository.findById(i).orElse(null));
            if(nastavnik != null){
                dtos.add(nastavnik);
            }
        }
        return dtos;
    }

    
    @Override
    public List<PohadjanjePredmetaDTO> findByStartDate(Date from, Date to) {
        List<PohadjanjePredmetaDTO> dtos = new ArrayList<>();
        List<PohadjanjePredmeta> pohadjanja = pohadjanjePredmetaRepository.findByStartDate(from, to);
        for(PohadjanjePredmeta p : pohadjanja){
            dtos.add(new PohadjanjePredmetaDTO(p));
        }
        return dtos;
    }

    @Override
    public List<PohadjanjePredmetaDTO> findByEndDate(Date endDate) {
        List<PohadjanjePredmetaDTO> dtos = new ArrayList<>();
        List<PohadjanjePredmeta> pohadjanja = pohadjanjePredmetaRepository.findByStartDate(endDate, endDate);
        for(PohadjanjePredmeta p : pohadjanja){
            dtos.add(new PohadjanjePredmetaDTO(p));
        }
        return dtos;
    }


    public PohadjanjePredmeta populatePohadjanjePredmeta(PohadjanjePredmetaDTO pohadjanjePredmeta, PohadjanjePredmeta novo){
        if(pohadjanjePredmeta.getStartDate() != null){
            novo.setStartDate(pohadjanjePredmeta.getStartDate());
        }
        if(pohadjanjePredmeta.getEndDate() != null){
            novo.setEndDate(pohadjanjePredmeta.getEndDate());
        }
        if(pohadjanjePredmeta.getPredmet() != null){
            novo.setPredmet(predmetRepository.findById(pohadjanjePredmeta.getPredmet().getId()).orElse(new Predmet()));
        }
        List<Student> studenti = new ArrayList<>();
        if(pohadjanjePredmeta.getStudenti() != null){
            for(StudentDTO studentDTO : pohadjanjePredmeta.getStudenti()){
                Student student = studentRepository.findByKorisnickoIme(studentDTO.getKorisnickoIme());
                studenti.add(student);
            }
            novo.setStudenti(new HashSet<>(studenti));
        }
        return novo;
    }


   


}
