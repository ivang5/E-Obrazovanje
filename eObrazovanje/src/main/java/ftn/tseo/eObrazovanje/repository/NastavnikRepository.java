package ftn.tseo.eObrazovanje.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ftn.tseo.eObrazovanje.model.Nastavnik;
import ftn.tseo.eObrazovanje.model.Predmet;

@Repository
public interface NastavnikRepository extends JpaRepository<Nastavnik, Long> {

    Nastavnik findByKorisnickoIme(String korisnickoIme);
    @Query(value = "SELECT pp.predmet_id FROM predavanje_predmeta pp WHERE pp.nastavnik_id = ?1", nativeQuery = true)
    List<Predmet> getPredaje(Long id);
}
