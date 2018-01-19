package com.isfce.pidw.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor()
@Entity(name = "TEVALUATION")
public class Evaluation {

	@Pattern(regexp = "[A-Z]{2,8}[0-9]{0,3}", message = "{cours.code}")
	@Id
	@Column(length = 10)
	private String code;

	@NotNull
	@Size(min = 4, max = 60, message = "{elem.nom}")
	@Column(nullable = false, length = 50)
	private String nom;

	@Column(length = 20)
	private String langue;

	@Min(value = 1, message = "{cours.nbPeriodes}")
	@Column(nullable = false)
	private short nbPeriodes;

	@Getter // ne crée pas de setter
	@ElementCollection
	@CollectionTable(name = "TSECTION", // nom de la table
			joinColumns = @JoinColumn(name = "FKCOURS")) // nom de la clé étrangère
	@Column(name = "SECTION") // nom du champ (par defaut ce serait: sections)
	protected Set<String> sections = new HashSet<String>();
	
//	@OneToMany(mappedBy="cours",cascade=CascadeType.PERSIST)
//	protected Collection<Module> modules= new ArrayList<>();

	public Evaluation(String code, String nom, short nbPeriodes) {
		super();
		this.code = code;
		this.nom = nom;
		this.nbPeriodes = nbPeriodes;
	}

	public void addSection(String section) {
		sections.add(section);
	}
	
	

	public void removeSection(String section) {
		sections.remove(section);
	}
}
