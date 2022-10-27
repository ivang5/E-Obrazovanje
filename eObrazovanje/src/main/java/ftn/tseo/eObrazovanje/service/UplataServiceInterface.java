package ftn.tseo.eObrazovanje.service;

import java.util.List;

import ftn.tseo.eObrazovanje.dto.*;

public interface UplataServiceInterface {

    public UplataDTO findOne(Long id);

    public List<UplataDTO> findAll();

    public List<UplataDTO> findByStudent(Long id);

    public void save(UplataDTO uplata);

    public void update(UplataDTO uplata);

    public void remove(Long id);

}
