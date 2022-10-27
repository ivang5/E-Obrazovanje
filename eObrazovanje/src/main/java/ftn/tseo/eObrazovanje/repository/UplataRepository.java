package ftn.tseo.eObrazovanje.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ftn.tseo.eObrazovanje.model.Uplata;

@Repository
public interface UplataRepository extends JpaRepository<Uplata, Long> {

    @Query(value = "SELECT * FROM uplata dok WHERE dok.student_id = ?1", nativeQuery = true)
    List<Uplata> findByStudent(Long id);

}
