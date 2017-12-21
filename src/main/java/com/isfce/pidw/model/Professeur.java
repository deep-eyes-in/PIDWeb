package com.isfce.pidw.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="TPROFESSEUR")
public class Professeur {
	@Id
	private String code;
	private String nom;
	private String prenom;
	private String email;
}
