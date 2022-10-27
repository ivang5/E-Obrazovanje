package ftn.tseo.eObrazovanje.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@SuppressWarnings("unused")
public class PredispitnaObaveza {

	public enum TipTesta {
		ISPIT,
		KOLOKVIJUM,
		PROJEKAT
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private TipTesta tipTesta;
	
	@OneToOne
	@JoinColumn(name = "predmet_id", referencedColumnName = "id", nullable = false)
	private Predmet predmet;

	private LocalDate datum;
	private String sala;
}
