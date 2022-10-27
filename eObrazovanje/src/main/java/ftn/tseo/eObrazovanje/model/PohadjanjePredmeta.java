package ftn.tseo.eObrazovanje.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
public class PohadjanjePredmeta {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private Date startDate;
	
	private Date endDate;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade =
	{
		CascadeType.DETACH,
		CascadeType.MERGE,
		CascadeType.REFRESH,
		CascadeType.PERSIST
	})
	@JoinTable(
			name="pohadjanje_predmeta_studenti",
			joinColumns = @JoinColumn(name="pohadjanje_predmeta_id"),
			inverseJoinColumns = @JoinColumn(name="student_id"))
	private Set<Student> studenti = new HashSet<Student>();
	
	@OneToOne
	@JoinColumn(name = "predmet_id", referencedColumnName = "id", nullable = false)
	private Predmet predmet;
	
	
	
	
}
