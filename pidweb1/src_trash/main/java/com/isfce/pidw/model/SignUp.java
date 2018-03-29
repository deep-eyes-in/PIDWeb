package com.isfce.pidw.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor()
public class SignUp {



//	private String module ;
	List<String> modules = new ArrayList<>();  //  HashSet<>();
	
	private String etudiant ;
	
	
		
	
	

	public SignUp( List<String> modules, String etudiant ) {
		super();
//		this.module = module;
		this.modules = modules;
		this.etudiant = etudiant;
	}

}






