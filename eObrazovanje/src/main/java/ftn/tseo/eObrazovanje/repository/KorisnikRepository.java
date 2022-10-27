package ftn.tseo.eObrazovanje.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ftn.tseo.eObrazovanje.model.Korisnik;

@Repository
public interface KorisnikRepository extends JpaRepository<Korisnik, Long> {
    
    Korisnik findByKorisnickoIme(String korisnickoIme);

    @Query(value = "SELECT k.tip_korisnika FROM korisnik k WHERE k.id = ?1", nativeQuery = true)
    String getRole(Long id);
}
