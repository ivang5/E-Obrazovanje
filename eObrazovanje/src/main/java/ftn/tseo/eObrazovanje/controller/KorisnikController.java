package ftn.tseo.eObrazovanje.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ftn.tseo.eObrazovanje.dto.AdministratorDTO;
import ftn.tseo.eObrazovanje.dto.KorisnikDTO;
import ftn.tseo.eObrazovanje.dto.NastavnikDTO;
import ftn.tseo.eObrazovanje.dto.StudentDTO;
import ftn.tseo.eObrazovanje.model.Administrator;
import ftn.tseo.eObrazovanje.model.Korisnik;
import ftn.tseo.eObrazovanje.model.Nastavnik;
import ftn.tseo.eObrazovanje.model.Student;
import ftn.tseo.eObrazovanje.security.TokenUtils;
import ftn.tseo.eObrazovanje.service.AdministratorServiceInterface;
import ftn.tseo.eObrazovanje.service.KorisnikServiceInterface;
import ftn.tseo.eObrazovanje.service.NastavnikServiceInterface;
import ftn.tseo.eObrazovanje.service.StudentServiceInterface;
import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "korisnici")
@RequiredArgsConstructor
public class KorisnikController {

    private final KorisnikServiceInterface korisnikService;
    private final AdministratorServiceInterface administratorService;
    private final NastavnikServiceInterface nastavnikService;
    private final StudentServiceInterface studentService;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final TokenUtils tokenUtils;
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody KorisnikDTO korisnikDTO) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(korisnikDTO.getKorisnickoIme(), korisnikDTO.getLozinka());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(korisnikDTO.getKorisnickoIme());
            return ResponseEntity.ok(tokenUtils.generateToken(userDetails));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping
    public ResponseEntity<List<KorisnikDTO>> getAllKorisnici() {
        return ResponseEntity.ok().body(korisnikService.findAll());
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping(value = "/administratori")
    public ResponseEntity<List<AdministratorDTO>> getAllAdministratori() {
        return ResponseEntity.ok().body(administratorService.findAll());
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'NASTAVNIK')")
    @GetMapping(value = "/nastavnici")
    public ResponseEntity<List<NastavnikDTO>> getAllNastavnici() {
        return ResponseEntity.ok().body(nastavnikService.findAll());
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'NASTAVNIK')")
    @GetMapping(value = "/studenti")
    public ResponseEntity<List<StudentDTO>> getAllStudenti() {
        return ResponseEntity.ok().body(studentService.findAll());
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping(value = "/{username}")
    public ResponseEntity<KorisnikDTO> getKorisnikByUsername(@PathVariable("username") String username) {
        Korisnik korisnik = korisnikService.findByKorisnickoIme(username);

        if (korisnik == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(new KorisnikDTO(korisnik));
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping(value = "/administratori/{username}")
    public ResponseEntity<AdministratorDTO> getAdministratorByUsername(@PathVariable("username") String username) {
        Administrator administrator = administratorService.findByKorisnickoIme(username);

        if (administrator == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(new AdministratorDTO(administrator));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'NASTAVNIK')")
    @GetMapping(value = "/nastavnici/{username}")
    public ResponseEntity<NastavnikDTO> getNastavnikByUsername(@PathVariable("username") String username) {
        Nastavnik nastavnik = nastavnikService.findByKorisnickoIme(username);

        if (nastavnik == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(new NastavnikDTO(nastavnik));
    }

    @GetMapping(value = "/studenti/{username}")
    public ResponseEntity<StudentDTO> getStudentByUsername(@PathVariable("username") String username) {
        Student student = studentService.findByKorisnickoIme(username);

        if (student == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(new StudentDTO(student));
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PostMapping(value = "/administratori")
    public ResponseEntity<?> createAdministrator(@RequestBody AdministratorDTO admin) {
        boolean checkUsername = isUsernameFree(admin.getKorisnickoIme());

        if(!checkUsername) {
            return ResponseEntity.unprocessableEntity().body("Vec postoji korisnik sa ovim korisnickim imenom!");
        }

        try {
            administratorService.save(admin);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Bad request, something went wrong");
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PostMapping(value = "/nastavnici")
    public ResponseEntity<?> createNastavnik(@RequestBody NastavnikDTO nastavnik) {
        boolean checkUsername = isUsernameFree(nastavnik.getKorisnickoIme());

        if(!checkUsername) {
            return ResponseEntity.unprocessableEntity().body("Vec postoji korisnik sa ovim korisnickim imenom!");
        }

        try {
            nastavnikService.save(nastavnik);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Bad request, something went wrong");
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PostMapping(value = "/studenti")
    public ResponseEntity<?> createStudent(@RequestBody StudentDTO student) {
        boolean checkUsername = isUsernameFree(student.getKorisnickoIme());

        if(!checkUsername) {
            return ResponseEntity.unprocessableEntity().body("Vec postoji korisnik sa ovim korisnickim imenom!");
        }

        try {
            studentService.save(student);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Bad request, something went wrong");
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PutMapping(value = "/administratori")
    public ResponseEntity<?> updateAdministrator(@RequestBody AdministratorDTO admin) {
        try {
            administratorService.update(admin);
        } catch (Exception e) {
            return new ResponseEntity<String>("Bad request, something went wrong", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'NASTAVNIK')")
    @PutMapping(value = "/nastavnici")
    public ResponseEntity<?> updateNastavnik(@RequestBody NastavnikDTO nastavnik) {
        try {
            nastavnikService.update(nastavnik);
        } catch (Exception e) {
            return new ResponseEntity<String>("Bad request, something went wrong", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'STUDENT')")
    @PutMapping(value = "/studenti")
    public ResponseEntity<?> updateStudent(@RequestBody StudentDTO student) {
        try {
            studentService.update(student);
        } catch (Exception e) {
            return new ResponseEntity<String>("Bad request, something went wrong", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteKorisnik(@PathVariable Long id) {
        Korisnik korisnik = korisnikService.findOne(id);

        if (korisnik != null) {
            korisnikService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public boolean isUsernameFree(String korIme) {
        Korisnik korisnik = korisnikService.findByKorisnickoIme(korIme);

        if (korisnik == null) {
            return true;
        }

        return false;
    }
}
