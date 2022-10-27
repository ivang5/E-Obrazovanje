package ftn.tseo.eObrazovanje.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.tseo.eObrazovanje.dto.UplataDTO;
import ftn.tseo.eObrazovanje.model.Uplata;
import ftn.tseo.eObrazovanje.repository.StudentRepository;
import ftn.tseo.eObrazovanje.repository.UplataRepository;

@Service
@Transactional
public class UplataService implements UplataServiceInterface {

    @Autowired
    private UplataRepository uplataRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public UplataDTO findOne(Long id) {

        return new UplataDTO(uplataRepository.findById(id).orElse(null));
    }

    @Override
    public List<UplataDTO> findAll() {

        List<UplataDTO> uplataDTO = new ArrayList<UplataDTO>();

        for (Uplata u : uplataRepository.findAll()) {
            uplataDTO.add(new UplataDTO(u));
        }

        return uplataDTO;
    }

    @Override
    public void save(UplataDTO uplataDTO) {

        Uplata uplata = new Uplata();
        populateUplata(uplata, uplataDTO);

    }

    @Override
    public void update(UplataDTO uplataDTO) {

        Uplata uplata = uplataRepository.getById(uplataDTO.getId());

        if (uplata != null) {
            populateUplata(uplata, uplataDTO);
        }

    }

    @Override
    public void remove(Long id) {
        uplataRepository.deleteById(id);
    }

    private void populateUplata(Uplata uplata, UplataDTO uplataDTO) {

        uplata.setSvrha(uplataDTO.getSvrha());
        uplata.setIznos(uplataDTO.getIznos());
        uplata.setDatum(uplataDTO.getDatum());
        uplata.setStudent(studentRepository.getById(uplataDTO.getStudent().getId()));

        uplataRepository.save(uplata);

    }

    @Override
    public List<UplataDTO> findByStudent(Long id) {
        List<Uplata> uplate = uplataRepository.findByStudent(id);
        List<UplataDTO> uplataDTO = new ArrayList<>();
        for (Uplata u : uplate) {
            UplataDTO uDto = new UplataDTO(u);
            uplataDTO.add(uDto);
        }

        return uplataDTO;
    }

}
