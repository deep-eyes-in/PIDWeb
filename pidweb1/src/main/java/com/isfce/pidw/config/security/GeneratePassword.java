package com.isfce.pidw.config.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
/**
 * Petite fonction qui permet de connaître le PW codé pour l'insérer dans la BD
 * manuellement
 * @author Didier
 *
 */
public class GeneratePassword {
	public PasswordEncoder encoder() {
			 return new StandardPasswordEncoder("53cr3t");
			 }
 public static void main(String[] args) {
	GeneratePassword o=new GeneratePassword();
	String pwCode=o.encoder().encode("DH");
	System.out.println("taille"+pwCode.length()+" code= "+pwCode);
		
	}

}
