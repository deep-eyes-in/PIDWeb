
package com.isfce.pidw.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor()
@Entity(name = "TCOURS")
public class Cours {

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
//	protected List<String> sections = new ArrayList<String>();
	
	
	public Cours(String code, String nom, short nbPeriodes) {
		super();
		this.code = code;
		this.nom = nom;
		this.nbPeriodes = nbPeriodes;
	}
	
	//		{IPID, FranÃ§ais, 30, Projet de Developpement et d'integration, null}
	public Cours(String code, String langue, short nbPeriodes, String nom, Set<String> sections ) {
		super();
		this.code = code;
		this.langue = langue;
		this.nbPeriodes = nbPeriodes;
		this.nom = nom;
		this.sections = sections;
	}

	public void addSection(String section) {
		sections.add(section);
	}

	public void removeSection(String section) {
		sections.remove(section);
	}
	
	
	
	public Set <String> getSection( ) {
//		System.out.println(  sections.iterator().next().toString()   );
//		sections.get(0)
		
		return   sections;  //  
	}
	
	public void setSection(String section) {
		  sections.add(section);
	}
	
	
	
}
