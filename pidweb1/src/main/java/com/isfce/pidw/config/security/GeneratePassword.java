package com.isfce.pidw.config.security;

import static org.junit.Assert.assertTrue;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
/**
 * Petite fonction qui permet de connaître le PW codé pour l'insérer dans la BD
 * manuellement
 * @author Didier
 *
 */
public  class GeneratePassword {
	public PasswordEncoder encoder() {
			 return new StandardPasswordEncoder("53cr3t");
	}
	

/*	
 public static void main(String[] args) {
	 
	GeneratePassword o =new GeneratePassword();
	String pwCode=o.encoder().encode("DH");
	System.out.println("taille"+pwCode.length()+" code= "+pwCode);
	
	
	StandardPasswordEncoder encoder = new StandardPasswordEncoder("53cr3t");
	String result = encoder.encode("DH");
	System.out.println("taille"+result.length()+" code= "+result);
	

//	now your result is equal to `9e7e3a73a40871d4b489adb746c31ace280d28206dded9665bac40eabfe6ffdc32a8c5c416b5878f` 

//	String passworddb =  	getPasswordFromDB();

//	passworddb from daabase is `9e7e3a73a40871d4b489adb746c31ace280d28206dded9665bac40eabfe6ffdc32a8c5c416b5878f`

	System.out.println( encoder.matches( pwCode , result)) ;
	
	

	}
	
*/

	
	public static  String PasswordEncode( String str ) {
		
		PasswordEncoder gnr = new StandardPasswordEncoder("53cr3t") ;
		
		 return gnr.encode( str ) ;
	}
	
	
}



