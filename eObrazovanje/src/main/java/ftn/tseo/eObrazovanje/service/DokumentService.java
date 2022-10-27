package ftn.tseo.eObrazovanje.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.tseo.eObrazovanje.dto.DokumentDTO;
import ftn.tseo.eObrazovanje.dto.StudentDTO;
import ftn.tseo.eObrazovanje.model.Dokument;
import ftn.tseo.eObrazovanje.model.Student;
import ftn.tseo.eObrazovanje.repository.DokumentRepository;
import ftn.tseo.eObrazovanje.repository.StudentRepository;

@Service
@Transactional
@SuppressWarnings("unused")
public class DokumentService implements DokumentServiceInterface {

    @Autowired
    private DokumentRepository dokumentRepository;
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public DokumentDTO findOne(Long id) {

        return new DokumentDTO(dokumentRepository.findById(id).orElse(null));
    }

    @Override
    public List<DokumentDTO> findAll() {

        List<DokumentDTO> dokumentiDTO = new ArrayList<DokumentDTO>();

        for (Dokument d : dokumentRepository.findAll()) {
            dokumentiDTO.add(new DokumentDTO(d));
        }

        return dokumentiDTO;
    }

    @Override
    public void save(DokumentDTO dokumentDTO) {

        Dokument dokument = new Dokument();

        populateDokument(dokument, dokumentDTO);

    }

    @Override
    public void update(DokumentDTO dokumentDTO) {

        Dokument dokument = dokumentRepository.getById(dokumentDTO.getId());

        if (dokument != null) {
            populateDokument(dokument, dokumentDTO);
        }

    }

    @Override
    public void remove(Long id) {
        dokumentRepository.deleteById(id);
    }

    private void populateDokument(Dokument dokument, DokumentDTO dokumentDTO) {
        dokument.setNaziv(dokumentDTO.getNazivDokumenta());
        dokument.setTipDokumenta(dokumentDTO.getTipDokumenta());
        dokument.setStudent(studentRepository.getById(dokumentDTO.getStudent().getId()));

        dokumentRepository.save(dokument);
    }

    @Override
    public List<DokumentDTO> findByStudent(Long id) {
        List<Dokument> dokumenti = dokumentRepository.findByStudent(id);
        List<DokumentDTO> dokumentDTO = new ArrayList<>();
        for (Dokument u : dokumenti) {
            DokumentDTO dDto = new DokumentDTO(u);
            dokumentDTO.add(dDto);
        }

        return dokumentDTO;
    }

}
