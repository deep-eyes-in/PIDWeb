package com.isfce.pidw.model;

import lombok.Data;
import lombok.NoArgsConstructor;




@Data
@NoArgsConstructor()
public class CompetenceValid {


	private Long competenceId ;
	
	private String description ;
	
	private String  username;

	private Boolean valided;
	


	public CompetenceValid(Long competenceId, String username ) {
		super();
		this.competenceId = competenceId;
		this.username = username;

	}


}









