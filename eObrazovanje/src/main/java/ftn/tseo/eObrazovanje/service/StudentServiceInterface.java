package ftn.tseo.eObrazovanje.service;

import java.util.List;

import ftn.tseo.eObrazovanje.dto.StudentDTO;
import ftn.tseo.eObrazovanje.model.Student;

public interface StudentServiceInterface {
    
    public Student findOne(Long id);
    public Student findByKorisnickoIme(String username);
    public List<StudentDTO> findAll();
    public void save(StudentDTO student);
    public void update(StudentDTO student);
    public void remove(Long id);
}
