package com.isfce.pidw.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;




@Data
@NoArgsConstructor()
@Entity(name = "TCOMPETENCE")
public class Competence {

	
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO) 
	Long id;

	
	
	@NotNull
	@Size(min = 4, max = 60, message = "{elem.competence}")
	@Column(nullable = false, length = 50)
	private String description;	
	
	

	@NotNull
	@ManyToOne
	@JoinColumn(name = "FKCours", nullable = false)
	private Cours cours;

	
	
	@ManyToMany (cascade=CascadeType.PERSIST, fetch=FetchType.EAGER)
	@JoinTable(name = "TCOMPETENCE_VALID", joinColumns = @JoinColumn(name = "FKCOMPETENCE"),
	    inverseJoinColumns = @JoinColumn(name = "FKETUDIANT"))
//	private List<Etudiant> etudiants = new ArrayList<>(); 
	protected Set<Etudiant> etudiants = new HashSet<>();
	


	public Competence(Long id, String description, Cours cours) {
		super();
		this.id = id;
		this.description = description;
		this.cours = cours ;
	}


}









