package com.isfce.pidw.model;

import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.NoArgsConstructor;
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

	@Column
	private Date dateDebut;
	@Column
	private Date dateFin;

	@NotNull
	@Column(nullable = false)
	private MAS moment;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "FKCours", nullable = false)
	private Cours cours;

	public Module(String code, Date dateDebut, Date dateFin, MAS moment, Cours cours) {
		super();
		this.code = code;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.moment = moment;
		this.cours = cours;
	}
}
