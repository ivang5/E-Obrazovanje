package ftn.tseo.eObrazovanje.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ftn.tseo.eObrazovanje.dto.AdministratorDTO;
import ftn.tseo.eObrazovanje.model.Administrator;
import ftn.tseo.eObrazovanje.repository.AdministratorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class AdministratorService implements AdministratorServiceInterface {
    
    private final AdministratorRepository administratorRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Administrator findOne(Long id) {
        log.info("Pronalazenje administratora {}", id);
        return administratorRepository.getById(id);
    }

    @Override
    public Administrator findByKorisnickoIme(String korisnickoIme) {
        log.info("Pronalazenje administratora {}", korisnickoIme);
        return administratorRepository.findByKorisnickoIme(korisnickoIme);
    }

    @Override
    public List<AdministratorDTO> findAll() {
        log.info("Pronalazenje svih administratora");
        List<AdministratorDTO> administratoriDTO = new ArrayList<AdministratorDTO>();

		for(Administrator a : administratorRepository.findAll()) {
			administratoriDTO.add(new AdministratorDTO(a));
		}
        
		return administratoriDTO;
    }

    @Override
    public void save(AdministratorDTO administratorDTO) {
        log.info("Cuvanje novog administratora {} u bazi", administratorDTO.getId());
        Administrator administrator = new Administrator();
        populateAdministrator(administrator, administratorDTO, false);
    }

    @Override
    public void update(AdministratorDTO administratorDTO) {
        log.info("Azuriranje administratora {}", administratorDTO.getId());
        Administrator administrator = administratorRepository.getById(administratorDTO.getId());

        if (administrator != null) {
            populateAdministrator(administrator, administratorDTO, true);
        }
    }

    @Override
    public void remove(Long id) {
        log.info("Brisanje administratora {}", id);
        administratorRepository.deleteById(id);
    }

    private void populateAdministrator(Administrator administrator, AdministratorDTO administratorDTO, boolean izmena) {
        administrator.setIme(administratorDTO.getIme());
        administrator.setPrezime(administratorDTO.getPrezime());
        administrator.setKorisnickoIme(administratorDTO.getKorisnickoIme());
        if (!izmena || !administrator.getLozinka().equals(administratorDTO.getLozinka())) {
            administrator.setLozinka(passwordEncoder.encode(administratorDTO.getLozinka()));
        }
        administrator.setEmail(administratorDTO.getEmail());
        administrator.setAdresa(administratorDTO.getAdresa());
        administrator.setGrad(administratorDTO.getGrad());
        administrator.setBrojTelefona(administratorDTO.getBrojTelefona());
        
        administratorRepository.save(administrator);
    }
}
