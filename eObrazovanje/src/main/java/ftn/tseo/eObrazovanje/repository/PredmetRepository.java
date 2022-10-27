package ftn.tseo.eObrazovanje.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ftn.tseo.eObrazovanje.model.Nastavnik;
import ftn.tseo.eObrazovanje.model.Predmet;

public interface PredmetRepository extends JpaRepository<Predmet, Long>{
    
    @Query(value = "SELECT pp.nastavnik_id FROM predavanje_predmeta pp WHERE pp.predmet_id = ?1", nativeQuery = true)
    List<Nastavnik> getPredavace(Long id);

    @Query(value = "select p.* from predmet  p " +
                    "left join predavanje_predmeta pp on pp.predmet_id = p.id " +
                    "left join korisnik k on k.id = pp.nastavnik_id " +
                    "where k.korisnicko_ime = ?1", nativeQuery = true)
    List<Predmet> findByPredavac(String korisnickoIme);
}
