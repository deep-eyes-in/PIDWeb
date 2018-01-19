package com.isfce.pidw.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
	@Column(length = 10)
	private Long id;

	
	
	@NotNull
	@Size(min = 4, max = 60, message = "{elem.competence}")
	@Column(nullable = false, length = 50)
	private String competence;	
	
	

	@NotNull
	@ManyToOne
	@JoinColumn(name = "FKCours", nullable = false)
	private Cours cours;

	
	
	


	public Competence(Long id, String competence, Cours cours) {
		super();
		this.id = id;
		this.competence = competence;
		this.cours = cours ;
	}


}









