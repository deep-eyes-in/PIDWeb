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
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@Column
	private Date dateDebut;
	
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@Column
	private Date dateFin;
	
	
	private String  dateFinTemp; 
	private String dateDebutTemp;

	@NotNull
	@Column(nullable = false)
	private MAS moment;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "FKCours", nullable = false)
	private Cours cours;
	
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
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.moment = moment;
		this.cours = cours;
		this.prof=prof;
	}
	
	
	public Date getDateDebut() throws ParseException {
		
			System.out.println( "LINE[87] getDateDebut() : dateDebut = " + dateDebut.toString() );
			return dateDebut;
		 
	}

	public Date getDateFin() throws ParseException {
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		//dateFin = sdf.parse(dateFin.toString());
		return dateFin;
	}
	
	public void setDateDebutTemp (String dateDebutStr) throws ParseException {
		Date  dateDebut = new SimpleDateFormat("yyyy-MM-dd").parse(dateDebutStr);
		this.dateDebut = dateDebut ;
	}
	
	public void setDateFinTemp (String dateFinStr) throws ParseException {
		Date dateFin = new SimpleDateFormat("yyyy-MM-dd").parse(dateFinStr);
		this.dateFin = dateFin ;
	}


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public MAS getMoment() {
		return moment;
	}

	public void setMoment(MAS moment) {
		this.moment = moment;
	}

	public Cours getCours() {
		return cours;
	}

	public void setCours(Cours cours) {
		System.out.println( "LINE[132]  = " + cours.toString() );
//		Cours c =  new Cours();
//		c.setCode(cours);
		//System.out.println( cours.toString() );
		this.cours = cours;
	}

	public Professeur getProf() {
		return prof;
	}

	public void setProf(Professeur prof) {
		this.prof = prof;
	}

	public Set<Etudiant> getEtudiants() {
		return etudiants;
	}

	public void setEtudiants(Set<Etudiant> etudiants) {
		this.etudiants = etudiants;
	}

	@Override
	public String toString() {
		
		return "Module " + "dateDebutTemp=" + dateFinTemp
				+ "[code=" + code + ", "
				+ "dateDebut=" + dateDebut + ", "
				+ "dateFin=" + dateFin + ", "
				+ "moment=" + moment
				+ ", cours=" + cours + ", "
				+ "prof=" + prof + ", "
				+ "etudiants=" + etudiants 
				+ "]";
	}

	public String getDateFinTemp() {
		return dateFinTemp;
	}

	public String getDateDebutTemp() {
		return dateDebutTemp;
	}

	
	
	
}
