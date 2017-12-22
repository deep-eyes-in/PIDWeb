package com.isfce.pidw.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
@Component
@SessionScope
public class Panier {
	private int nbCours;
	private List<Cours> cours=new ArrayList<>();
	public int getNbCours() {
		return nbCours;
	}
	public void setNbCours(int nbCours) {
		this.nbCours = nbCours;
	}
	public List<Cours> getCours() {
		return cours;
	}
	public void setCours(List<Cours> cours) {
		this.cours = cours;
	}
	

}
