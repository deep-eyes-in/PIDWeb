package com.isfce.pidw.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.isfce.pidw.filter.EvaluationKey;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor()
@Entity(name = "TEVALUATION")
@IdClass(  EvaluationKey.class  )
public class Evaluation {

	public static enum SESSION {
		PREMIERE, DEUXIEME
	}

	
	
	@Id
//	@NotNull
	@ManyToOne
	@JoinColumn(name = "FKMODULE", nullable = false)
	private Module module;
	
	
	
	@Id
//	@NotNull
	@ManyToOne
	@JoinColumn(name = "FKETUDIANT", nullable = false)
	private Etudiant etudiant;
	
	

	
	@Id
//	@NotNull
	@Column(nullable = false)
	private SESSION session;

	
//	@NotNull
	@Column(nullable = false)
	private Integer resultat = 0;
	
	
	
	
//	@OneToMany(mappedBy="cours",cascade=CascadeType.PERSIST)
//	protected Collection<Module> modules= new ArrayList<>();

	public Evaluation(  Etudiant etudiant, SESSION session, Integer resultat) {
		this.etudiant = etudiant;
		this.session = session;
		this.resultat = resultat;
	}
	
	
	
}






















