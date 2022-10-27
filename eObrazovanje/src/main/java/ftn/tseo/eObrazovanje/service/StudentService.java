package ftn.tseo.eObrazovanje.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ftn.tseo.eObrazovanje.dto.StudentDTO;
import ftn.tseo.eObrazovanje.model.Student;
import ftn.tseo.eObrazovanje.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class StudentService implements StudentServiceInterface {
    
    private final StudentRepository studentRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Student findOne(Long id) {
        log.info("Pronalazenje studenta {}", id);
        return studentRepository.getById(id);
    }

    @Override
    public Student findByKorisnickoIme(String korisnickoIme) {
        log.info("Pronalazenje studenta {}", korisnickoIme);
        return studentRepository.findByKorisnickoIme(korisnickoIme);
    }

    @Override
    public List<StudentDTO> findAll() {
        log.info("Pronalazenje svih studenta");
        List<StudentDTO> studentiDTO = new ArrayList<StudentDTO>();

		for(Student s : studentRepository.findAll()) {
			studentiDTO.add(new StudentDTO(s));
		}
        
		return studentiDTO;
    }

    @Override
    public void save(StudentDTO studentDTO) {
        log.info("Cuvanje novog studenta {} u bazi", studentDTO.getKorisnickoIme());
        Student student = new Student();
        populateStudent(student, studentDTO, false);
    }

    @Override
    public void update(StudentDTO studentDTO) {
        log.info("Azuriranje studenta {}", studentDTO.getId());
        Student student = studentRepository.getById(studentDTO.getId());

        if (student != null) {
            populateStudent(student, studentDTO, true);
        }
    }

    @Override
    public void remove(Long id) {
        log.info("Brisanje studenta {}", id);
        studentRepository.deleteById(id);
    }

    private void populateStudent(Student student, StudentDTO studentDTO, boolean izmena) {
        student.setIme(studentDTO.getIme());
        student.setPrezime(studentDTO.getPrezime());
        student.setKorisnickoIme(studentDTO.getKorisnickoIme());
        if (!izmena || !student.getLozinka().equals(studentDTO.getLozinka())) {
            student.setLozinka(passwordEncoder.encode(studentDTO.getLozinka()));
        }
        student.setEmail(studentDTO.getEmail());
        student.setAdresa(studentDTO.getAdresa());
        student.setGrad(studentDTO.getGrad());
        student.setBrojTelefona(studentDTO.getBrojTelefona());
        student.setJmbg(studentDTO.getJmbg());
        student.setSmer(studentDTO.getSmer());
        student.setGodinaStudija(studentDTO.getGodinaStudija());
        if (!izmena) {
            student.setRacun(0);
        } else {
            student.setRacun(studentDTO.getRacun());
        }
        studentRepository.save(student);
    }
}
