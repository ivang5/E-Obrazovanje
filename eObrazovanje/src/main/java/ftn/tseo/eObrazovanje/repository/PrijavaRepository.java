package ftn.tseo.eObrazovanje.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ftn.tseo.eObrazovanje.model.Prijava;

@Repository
public interface PrijavaRepository extends JpaRepository<Prijava, Long> {

    @Query(value = "SELECT * FROM prijava p WHERE p.student_id = ?1 AND p.ocenjen = true", nativeQuery = true)
    List<Prijava> findByStudent(Long id);

    @Query(value = "SELECT p.* FROM prijava p JOIN predispitna_obaveza po ON po.id = p.predispitna_obaveza_id JOIN predavanje_predmeta pp ON po.predmet_id = pp.predmet_id WHERE pp.nastavnik_id = ?1", nativeQuery = true)
    List<Prijava> findByNastavnik(Long id);
}
