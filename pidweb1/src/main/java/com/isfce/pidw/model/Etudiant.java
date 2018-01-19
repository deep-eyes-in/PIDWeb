package com.isfce.pidw.model;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.isfce.pidw.config.security.Roles;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity(name = "TETUDIANT")
public class Etudiant extends Users {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;

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

	@Column(length = 30)
	private String tel;

	public Etudiant(String userName, String password, String nom, String prenom, String email, String tel) {
		super(userName, password,Roles.ROLE_ETUDIANT);
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.tel = tel;
	}

}
