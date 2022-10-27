package ftn.tseo.eObrazovanje.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ftn.tseo.eObrazovanje.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    
        Student findByKorisnickoIme(String korisnickoIme);
}
