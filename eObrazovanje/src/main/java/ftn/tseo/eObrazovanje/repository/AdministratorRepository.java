package ftn.tseo.eObrazovanje.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ftn.tseo.eObrazovanje.model.Administrator;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Long> {
    
    Administrator findByKorisnickoIme(String korisnickoIme);
}
