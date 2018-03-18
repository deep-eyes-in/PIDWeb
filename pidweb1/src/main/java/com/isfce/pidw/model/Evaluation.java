package com.isfce.pidw.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.isfce.pidw.filter.EvaluationKey;

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
	private Module module;
	
	
	
	@NotNull
	@Column(nullable = false)
	private SESSION session;
	

	
	@NotNull
	@Column(nullable = false)
	private Integer resultat = 0;
	

	@Column(name = "comments", nullable = true)
	private String comments = "";
	
	
	
	
//	@OneToMany(mappedBy="cours",cascade=CascadeType.PERSIST)
//	protected Collection<Module> modules= new ArrayList<>();

	public Evaluation( Long id, Etudiant etudiant, Module module, SESSION session, Integer resultat) {
		super();
		this.id = id;
		this.etudiant = etudiant;
		this.module = module;
		this.session = session;
		this.resultat = resultat;
	}
	
	public Evaluation( EvaluationKey infos,  Integer resultat ) {
		   
		this.etudiant = infos.getEtudiant() ;
		this.module = infos.getModule();
		this.session = infos.getSession();
		this.resultat = resultat;
		
	}
	
	
}






















