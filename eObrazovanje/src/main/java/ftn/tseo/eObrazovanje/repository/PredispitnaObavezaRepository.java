package ftn.tseo.eObrazovanje.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ftn.tseo.eObrazovanje.model.PredispitnaObaveza;

@Repository
public interface PredispitnaObavezaRepository extends JpaRepository<PredispitnaObaveza, Long> {

    @Query(value = "SELECT DISTINCT po.* FROM predispitna_obaveza po JOIN pohadjanje_predmeta pp ON po.predmet_id = pp.predmet_id JOIN pohadjanje_predmeta_studenti pps ON pp.id = pps.pohadjanje_predmeta_id JOIN prijava pr ON po.id = pr.predispitna_obaveza_id WHERE pr.student_id = ?1 AND po.id = pr.predispitna_obaveza_id", nativeQuery = true)
    List<PredispitnaObaveza> findPrijavljene(Long id);

    @Query(value = "SELECT DISTINCT po.* FROM predispitna_obaveza po JOIN pohadjanje_predmeta pp ON po.predmet_id = pp.predmet_id JOIN pohadjanje_predmeta_studenti pps ON pp.id = pps.pohadjanje_predmeta_id LEFT JOIN prijava pr ON po.id = pr.predispitna_obaveza_id WHERE pr.student_id != ?1 AND pr.predispitna_obaveza_id NOT IN (SELECT prr.predispitna_obaveza_id FROM prijava prr WHERE prr.student_id = ?1) OR pr.student_id IS NULL", nativeQuery = true)
    List<PredispitnaObaveza> findNeprijavljene(Long id);
}
