package ftn.tseo.eObrazovanje.model;

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
import javax.persistence.OneToMany;

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
public class Predmet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String naziv;
	private int ESPB;
//	@OneToMany(mappedBy = "predmet", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
//	private Set<PohadjanjePredmeta> pohadjanjaPredmeta = new HashSet<PohadjanjePredmeta>();
//	
	@ManyToMany(fetch = FetchType.EAGER, cascade =
	{
		CascadeType.DETACH,
		CascadeType.MERGE,
		CascadeType.REFRESH,
		CascadeType.PERSIST
	})
	@JoinTable(
			name="predavanje_predmeta",
			joinColumns = @JoinColumn(name="predmet_id"),
			inverseJoinColumns = @JoinColumn(name="nastavnik_id"))
	private Set<Nastavnik> predavaci = new HashSet<>();
}
