package ftn.tseo.eObrazovanje.model;

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
public class Dokument {

	public enum TipDokumenta {
		DIPLOMA,
		OBRAZAC
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String naziv;
	@Enumerated(EnumType.STRING)
	private TipDokumenta tipDokumenta;
	@OneToOne
	@JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false)
	private Student student;
}
