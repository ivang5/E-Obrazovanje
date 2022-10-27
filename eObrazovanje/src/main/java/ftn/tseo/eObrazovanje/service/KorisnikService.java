package ftn.tseo.eObrazovanje.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ftn.tseo.eObrazovanje.dto.KorisnikDTO;
import ftn.tseo.eObrazovanje.model.Korisnik;
import ftn.tseo.eObrazovanje.repository.KorisnikRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class KorisnikService implements KorisnikServiceInterface {
    
    private final KorisnikRepository korisnikRepository;

    @Override
    public Korisnik findOne(Long id) {
        log.info("Pronalazenje korisnika {}", id);
        return korisnikRepository.getById(id);
    }

    @Override
    public Korisnik findByKorisnickoIme(String korisnickoIme) {
        log.info("Pronalazenje korisnika {}", korisnickoIme);
        return korisnikRepository.findByKorisnickoIme(korisnickoIme);
    }

    @Override
    public List<KorisnikDTO> findAll() {
        log.info("Pronalazenje svih korisnika");
        List<KorisnikDTO> korisniciDTO = new ArrayList<KorisnikDTO>();

		for(Korisnik k : korisnikRepository.findAll()) {
			korisniciDTO.add(new KorisnikDTO(k));
		}
        
		return korisniciDTO;
    }

    @Override
    public void save(KorisnikDTO korisnikDTO) {
        log.info("Cuvanje novog korisnika {} u bazi", korisnikDTO.getId());
        Korisnik korisnik = new Korisnik();
        populateKorisnik(korisnik, korisnikDTO);
    }

    @Override
    public void update(KorisnikDTO korisnikDTO) {
        log.info("Azuriranje korisnika {}", korisnikDTO.getId());
        Korisnik korisnik = korisnikRepository.getById(korisnikDTO.getId());

        if (korisnik != null) {
            populateKorisnik(korisnik, korisnikDTO);
        }
    }

    @Override
    public void remove(Long id) {
        log.info("Brisanje korisnika {}", id);
        korisnikRepository.deleteById(id);
    }

    @Override
    public String getRole(Long id) {
        log.info("Provera uloge za korisnika {}", id);
        String role = korisnikRepository.getRole(id);
        return role;
    }

    private void populateKorisnik(Korisnik korisnik, KorisnikDTO korisnikDTO) {
        korisnik.setIme(korisnikDTO.getIme());
        korisnik.setPrezime(korisnikDTO.getPrezime());
        korisnik.setKorisnickoIme(korisnikDTO.getKorisnickoIme());
        korisnik.setLozinka(korisnikDTO.getLozinka());
        korisnik.setEmail(korisnikDTO.getEmail());
        korisnik.setAdresa(korisnikDTO.getAdresa());
        korisnik.setGrad(korisnikDTO.getGrad());
        korisnik.setBrojTelefona(korisnikDTO.getBrojTelefona());
        
        korisnikRepository.save(korisnik);
    }
}
