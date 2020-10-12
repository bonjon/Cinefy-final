package logic.entities;

/*
 * Classe entity Risposta che rappresenta la tabella Risposta 
 */

public class Risposta {

	private int id;
	private String contenuto;
	private String advancedName;
	private String beginnerName;
	private int idDomanda;

	public Risposta(int id, String contenuto, String advancedName, String beginnerName, int idDomanda) {
		this.id = id;
		this.contenuto = contenuto;
		this.advancedName = advancedName;
		this.beginnerName = beginnerName;
		this.idDomanda = idDomanda;
	}

	public int getId() {
		return id;
	}

	public String getContenuto() {
		return contenuto;
	}

	public String getAdvancedName() {
		return advancedName;
	}

	public String getBeginnerName() {
		return beginnerName;
	}

	public int getIdDomanda() {
		return idDomanda;
	}

}
