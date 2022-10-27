package ftn.tseo.eObrazovanje.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ftn.tseo.eObrazovanje.model.Dokument;

@Repository
public interface DokumentRepository extends JpaRepository<Dokument, Long> {

    @Query(value = "SELECT * FROM dokument dok WHERE dok.student_id = ?1", nativeQuery = true)
    List<Dokument> findByStudent(Long id);

}
