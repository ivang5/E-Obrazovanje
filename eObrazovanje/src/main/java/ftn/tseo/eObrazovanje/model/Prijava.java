package ftn.tseo.eObrazovanje.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Prijava {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
    private boolean ocenjen;
    private boolean polozen;
	private int bodovi;

	@OneToOne
	@JoinColumn(name = "predispitna_obaveza_id", referencedColumnName = "id", nullable = false)
	private PredispitnaObaveza predispitnaObaveza;

    @OneToOne
	@JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false)
	private Student student;
}
