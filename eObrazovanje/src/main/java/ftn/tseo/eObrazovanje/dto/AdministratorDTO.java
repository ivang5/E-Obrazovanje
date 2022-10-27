package ftn.tseo.eObrazovanje.dto;

import ftn.tseo.eObrazovanje.model.Administrator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdministratorDTO extends KorisnikDTO {
    
    public AdministratorDTO(Administrator administrator) {
        super(administrator);
    }
}
