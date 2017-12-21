package com.isfce.pidw.web;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ResponseStatus;
//Map cette exception sur une erreur HTTP 404 paramètrée
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Elément non trouvé !")
public class NotFoundException extends RuntimeException {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	//identifiant de l'objet recherche
	private String code;

	public NotFoundException(String message,String code) {
		super(message);
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
