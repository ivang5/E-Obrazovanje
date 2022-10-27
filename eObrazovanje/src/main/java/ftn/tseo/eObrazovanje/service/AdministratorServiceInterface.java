package ftn.tseo.eObrazovanje.service;

import java.util.List;

import ftn.tseo.eObrazovanje.dto.AdministratorDTO;
import ftn.tseo.eObrazovanje.model.Administrator;

public interface AdministratorServiceInterface {
    
    public Administrator findOne(Long id);
    public Administrator findByKorisnickoIme(String username);
    public List<AdministratorDTO> findAll();
    public void save(AdministratorDTO administrator);
    public void update(AdministratorDTO administrator);
    public void remove(Long id);
}
