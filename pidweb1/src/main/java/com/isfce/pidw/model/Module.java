package com.isfce.pidw.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Entity(name = "TMODULE")
public class Module {
	public static enum MAS {
		MATIN, APM, SOIR
	}

	@Id
	@Pattern(regexp = "[0-9]{0,2}[A-Z]{1,10}\\-[0-9]{1,2}\\-[A-Z]{1,2}", message = "{module.code}")
	@Column(length = 20)
	private String code;

	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column
	private Date dateDebut;
	
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column
	private Date dateFin;
	
	

	@NotNull
	@Column(nullable = false)
	private MAS moment;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "FKCours", nullable = false)
	private Cours cours;
	
//	@NotNull
	@ManyToOne
	@JoinColumn(name = "FKProf")
	private Professeur prof;

	@ManyToMany (cascade=CascadeType.PERSIST)
	@JoinTable(name = "TINSCRIPTION", joinColumns = @JoinColumn(name = "FKMODULE"),
	    inverseJoinColumns = @JoinColumn(name = "FKETUDIANT"))
	private Set<Etudiant> etudiants = new HashSet<>();

	public Module(String code, Date dateDebut, Date dateFin, MAS moment, Cours cours,Professeur prof) {
		super();
		this.code = code;
		this.dateDebut = new Date();
		this.dateFin = new Date();
		this.moment = moment;
		this.cours = cours;
		this.prof=prof;
		
//		this.myDate = new Date();		
	}
	
	
	
}
