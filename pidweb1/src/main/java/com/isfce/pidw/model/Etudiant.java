package com.isfce.pidw.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;


//@GeneratedValue(strategy = GenerationType.IDENTITY)
//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator_etudiant"  )
//@SequenceGenerator(name = "generator_etudiant", sequenceName = "gen_etudiant", allocationSize=1  )


@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity(name="TETUDIANT")
public class Etudiant {
	@Id	
	private Long id;
	
	@NotNull
	@Size(min = 1, max = 60, message = "{elem.nom}")
	@Column(length = 30, nullable = false)
	private String nom;
	
	@Column(length = 30)
	private String prenom;

	@Email(message="{email.nonValide}")
	@NotNull
	@Column(length = 30, nullable = false)
	private String email;
	
	
	@Column(length = 30)
	private String tel;
	
	public Etudiant(String nom, String prenom, String email, String tel) {
		super();
		this.id=null;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.tel = tel;
	}
}






















