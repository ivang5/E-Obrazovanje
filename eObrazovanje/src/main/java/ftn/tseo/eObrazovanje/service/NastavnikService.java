package ftn.tseo.eObrazovanje.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ftn.tseo.eObrazovanje.dto.NastavnikDTO;
import ftn.tseo.eObrazovanje.dto.PredmetDTO;
import ftn.tseo.eObrazovanje.model.Nastavnik;
import ftn.tseo.eObrazovanje.model.Predmet;
import ftn.tseo.eObrazovanje.repository.NastavnikRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class NastavnikService implements NastavnikServiceInterface {
    
    private final NastavnikRepository nastavnikRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Nastavnik findOne(Long id) {
        log.info("Pronalazenje nastavnika {}", id);
        return nastavnikRepository.getById(id);
    }

    @Override
    public Nastavnik findByKorisnickoIme(String korisnickoIme) {
        log.info("Pronalazenje nastavnika {}", korisnickoIme);
        return nastavnikRepository.findByKorisnickoIme(korisnickoIme);
    }

    @Override
    public List<NastavnikDTO> findAll() {
        log.info("Pronalazenje svih nastavnika");
        List<NastavnikDTO> nastavniciDTO = new ArrayList<NastavnikDTO>();

		for(Nastavnik s : nastavnikRepository.findAll()) {
			nastavniciDTO.add(new NastavnikDTO(s));
		}
        
		return nastavniciDTO;
    }

    @Override
    public void save(NastavnikDTO nastavnikDTO) {
        log.info("Cuvanje novog nastavnika {} u bazi", nastavnikDTO.getId());
        Nastavnik nastavnik = new Nastavnik();
        populateNastavnik(nastavnik, nastavnikDTO, false);
    }

    @Override
    public void update(NastavnikDTO nastavnikDTO) {
        log.info("Azuriranje nastavnika {}", nastavnikDTO.getId());
        Nastavnik nastavnik = nastavnikRepository.getById(nastavnikDTO.getId());

        if (nastavnik != null) {
            populateNastavnik(nastavnik, nastavnikDTO, true);
        }
    }

    @Override
    public void remove(Long id) {
        log.info("Brisanje nastavnika {}", id);
        nastavnikRepository.deleteById(id);
    }

    private void populateNastavnik(Nastavnik nastavnik, NastavnikDTO nastavnikDTO, boolean izmena) {
        nastavnik.setIme(nastavnikDTO.getIme());
        nastavnik.setPrezime(nastavnikDTO.getPrezime());
        nastavnik.setKorisnickoIme(nastavnikDTO.getKorisnickoIme());
        if (!izmena || !nastavnik.getLozinka().equals(nastavnikDTO.getLozinka())) {
            nastavnik.setLozinka(passwordEncoder.encode(nastavnikDTO.getLozinka()));
        }
        nastavnik.setEmail(nastavnikDTO.getEmail());
        nastavnik.setAdresa(nastavnikDTO.getAdresa());
        nastavnik.setGrad(nastavnikDTO.getGrad());
        nastavnik.setBrojTelefona(nastavnikDTO.getBrojTelefona());
        nastavnik.setKatedra(nastavnikDTO.getKatedra());
        nastavnik.setUloga(nastavnikDTO.getUloga());
        
        nastavnikRepository.save(nastavnik);
    }

    public List<PredmetDTO> findPredaje(NastavnikDTO dto){
        Nastavnik nastavnik = findOne(dto.getId());
        List<PredmetDTO> predmetiDTO = new ArrayList<>();
        List<Predmet> predmeti = nastavnikRepository.getPredaje(nastavnik.getId());
        for(Predmet p : predmeti){
            predmetiDTO.add(new PredmetDTO(p));
        }
        return predmetiDTO;
    }
}
