package logic.bean;

import java.io.Serializable;

/*
 * Classe bean di Risposta che sarà interposta tra view e control
 */

public class RispostaBean implements Serializable {

	private static final long serialVersionUID = 42L;
	private String id;
	private String contenuto;
	private String advancedName;
	private String beginnerName;
	private String idDomanda;
	private String film;
	private String partecipant;
	private String genre;
	private String explanation; 
	private String profession;
	private String choice;

	public String getIdDomanda() {
		return idDomanda;
	}

	public void setIdDomanda(int idDomanda) {
		this.idDomanda = "" + idDomanda;
	}

	public String getId() {
		return id;
	}

	public void setId(int id) {
		this.id = "" + id;
	}

	public String getContenuto() {
		return contenuto;
	}

	public void setContenuto(String contenuto) {
		this.contenuto = contenuto;
	}

	public String getAdvancedName() {
		return advancedName;
	}

	public void setAdvancedName(String advancedName) {
		this.advancedName = advancedName;
	}

	public String getBeginnerName() {
		return beginnerName;
	}

	public void setBeginnerName(String beginnerName) {
		this.beginnerName = beginnerName;
	}
	
	public void setFilm(String film) {
		this.film=film;
	}
	
	public String getFilm() {
		return film;
	}
	
	public void setPartecipant(String partecipant) {
		this.partecipant=partecipant;
	}
	
	public String getPartecipant() {
		return partecipant;
	}
	
	public void setGenre(String genre) {
		this.genre=genre;
	}
	
	public String getGenre() {
		return genre;
	}

	public void setExplanation(String explanation) {
		this.explanation=explanation;
	}
	
	public String getExplanation() {
		return explanation;
	}
	
	public void setProfession(String profession) {
		this.profession=profession;
	}
	
	public String getProfession() {
		return profession;
	}
	
	public void setChoice(String choice) {
		this.choice = choice;
	}
	
	public String getChoice() {
		return choice;
	}
}

