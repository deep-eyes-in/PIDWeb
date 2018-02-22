package com.isfce.pidw.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.isfce.pidw.filter.CompetenceValidKey;

import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor()
@Entity(name = "TCOMPETENCE_VALID")
@IdClass(  CompetenceValidKey.class  )
public class CompetenceValid {
	
	
	
	@Id
	@ManyToOne
	@JoinColumn(name = "FKCOMPETENCE", nullable = false)
	private Competence competence;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "FKETUDIANT", nullable = false)
	private Etudiant etudiant ;
	
	public CompetenceValid( Competence competence, Etudiant etudiant ) {
		this.competence = competence ;
		this.etudiant = etudiant ;
	}
	
	
}




