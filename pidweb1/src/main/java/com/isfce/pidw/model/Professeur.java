package com.isfce.pidw.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import com.isfce.pidw.config.security.Roles;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity(name = "TPROFESSEUR")
public class Professeur extends Users {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Professeur(String username, String password, String nom, String prenom, String email) {
		super(username, password, Roles.ROLE_PROF);
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
	}

	@NotNull
	@Size(min = 1, max = 60, message = "{elem.nom}")
	@Column(length = 30, nullable = false)
	private String nom;

	@Column(length = 30)
	private String prenom;

	@Email(message = "{email.nonValide}")
	@NotNull
	@Column(length = 30, nullable = false)
	private String email;

}
