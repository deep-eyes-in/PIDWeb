package com.isfce.pidw.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;




@Data
@NoArgsConstructor()
public class CompetenceValid {




	private Long competenceId ;	
	
	
	private String  username;

	
	


	public CompetenceValid(Long competenceId, String username ) {
		super();
		this.competenceId = competenceId;
		this.username = username;

	}


}









