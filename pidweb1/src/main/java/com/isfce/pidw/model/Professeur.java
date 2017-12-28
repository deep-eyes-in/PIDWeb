package com.isfce.pidw.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity(name="TPROFESSEUR")
public class Professeur {
	@Pattern(regexp = "[A-Z]{2,8}[0-9]{0,3}", message = "{cours.code}")
	@Id
	@Column(length = 10)
	private String code;
	
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
	
	public Professeur(String code, String nom, String prenom, String email, String tel) {
		super();
		this.code=code;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.tel = tel;
	}
	
	
	
	
}
