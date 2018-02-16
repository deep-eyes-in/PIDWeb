package com.isfce.pidw.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor()
@Entity(name = "TEVALUATION")
public class Evaluation {

	public static enum SESSION {
		PREMIERE, DEUXIEME
	}
	

	@Id
	private Long id;
	
	
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "FKETUDIANT", nullable = false)
	private Etudiant etudiant;
	
	

	
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "FKMODULE", nullable = false)
	private Cours cours;
	
	
	@NotNull
	@Column(nullable = false)
	private SESSION session;

	
	@NotNull
	@Column(nullable = false)
	private Short resultat;
	
	
	
	
//	@OneToMany(mappedBy="cours",cascade=CascadeType.PERSIST)
//	protected Collection<Module> modules= new ArrayList<>();

	public Evaluation( Long id, Etudiant etudiant, Cours cours, SESSION session) {
		super();
		this.id = id;
		this.etudiant = etudiant;
		this.cours = cours;
		this.session = session;
	}
	
	
	
}






















