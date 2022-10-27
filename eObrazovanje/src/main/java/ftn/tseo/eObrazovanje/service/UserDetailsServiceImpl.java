package ftn.tseo.eObrazovanje.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ftn.tseo.eObrazovanje.model.Korisnik;

import java.util.ArrayList;
import java.util.List;

@Service
@Primary
public class UserDetailsServiceImpl implements UserDetailsService {
    
    @Autowired
    private KorisnikServiceInterface korisnikService;

    @Override
    public UserDetails loadUserByUsername(String korisnickoIme) throws UsernameNotFoundException {

        Korisnik korisnik = korisnikService.findByKorisnickoIme(korisnickoIme);

        if(korisnik == null){
            throw new UsernameNotFoundException("Ne postoji korisnik sa korisnickim imenom " + korisnickoIme);
        }else{
            String tipKorisnika = korisnikService.getRole(korisnik.getId());
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            String role = "ROLE_" + tipKorisnika;
            grantedAuthorities.add(new SimpleGrantedAuthority(role));

            return new org.springframework.security.core.userdetails.User(
                    korisnik.getKorisnickoIme().trim(),
                    korisnik.getLozinka().trim(),
                    grantedAuthorities);
        }
    }
}
