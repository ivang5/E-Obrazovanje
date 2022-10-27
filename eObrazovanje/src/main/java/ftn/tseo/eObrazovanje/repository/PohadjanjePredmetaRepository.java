package ftn.tseo.eObrazovanje.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ftn.tseo.eObrazovanje.model.PohadjanjePredmeta;

public interface PohadjanjePredmetaRepository  extends JpaRepository<PohadjanjePredmeta, Long>{
    

    @Query(value = "select pp.* from pohadjanje_predmeta pp where pp.predmet_id = ?1", nativeQuery = true)
    List<PohadjanjePredmeta> findPohadjanjeZaPredmet(Long id);

    @Query(value = "select pp.* from pohadjanje_predmeta pp left join pohadjanje_predmeta_studenti pps "+
                    "on pp.id = pps.pohadjanje_predmeta_id "+
                    "left join korisnik k " +
                    "on pps.student_id = k.id " +
                    "where k.korisnicko_ime = ?1", nativeQuery = true)
    List<PohadjanjePredmeta> findPohadjanjaStudenta(String korisnickoIme);

    @Query(value = "select k.id from korisnik k "+
                    "left join pohadjanje_predmeta_studenti pps"+
                    " on k.id = pps.student_id "+
                    "where pps.pohadjanje_predmeta_id = ?1", nativeQuery = true)
    List<Long> findStudenteZaPohadjanjePredmeta(Long id);

    @Query(value = "select pp.* from pohadjanje_predmeta pp "+
                    "left join predavanje_predmeta ppp "+
                    "on pp.id = ppp.predmet_id "+
                    "where ppp.nastavnik_id = ?1", nativeQuery = true)
    List<PohadjanjePredmeta> findForNastavnika(Long id);

    @Query(value = "select k.id from korisnik k "+
                    "left join predavanje_predmeta pp "+
                    "on pp.nastavnik_id = k.id "+
                    "where pp.predmet_id = ?1 ", nativeQuery = true)
    List<Long> findPredavace(Long id);

    @Query(value = "select pp.* pohadjanje_predmeta pp "+
                    "where start_date >= ?1 and start_date <= ?2 ", nativeQuery = true)
    List<PohadjanjePredmeta> findByStartDate(Date from, Date to);

    // List<PohadjanjePredmeta> findByEndDate(Date from, Date to);

    @Query(value = "select pp.* from pohadjanje_predmeta pp "+
                    "left join pohadjanje_predmeta_studenti pps "+
                    "on pp.id=pps.pohadjanje_predmeta_id "+ 
                    "left join korisnik k " +
                    "on k.id = pps.student_id " +
                    "where pp.predmet_id = ?1 and k.korisnicko_ime = ?2", nativeQuery = true)
    List<PohadjanjePredmeta> findPohadjanjePredmetaStudenta(Long id, String korisnickoIme);
    
}
