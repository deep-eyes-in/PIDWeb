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
import javax.persistence.ManyToOne;
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
public class SignUp {



	private Set<Etudiant> etudiants = new HashSet<>();
		
	
	
	
	
	
//	@OneToMany(mappedBy="cours",cascade=CascadeType.PERSIST)
//	protected Collection<Module> modules= new ArrayList<>();

	public SignUp(String code, Set<Etudiant> etudiants, short nbPeriodes) {
		super();
		this.code = code;
		this.nom = nom;
	}

	public void addSection(String section) {
		sections.add(section);
	}
	
	

	public void removeSection(String section) {
		sections.remove(section);
	}
}






