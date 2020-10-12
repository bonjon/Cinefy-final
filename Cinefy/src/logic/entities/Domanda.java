package logic.entities;

/*
 * Classe Entity Domanda che rappresenta la tabella Domanda 
 */

public class Domanda {
	
	private int id;
	private String contenuto;
	private String beginnerName;
	private String advancedName;

	public Domanda(int id, String contenuto, String beginnerName, String advancedName) {
		this.id = id;
		this.contenuto = contenuto;
		this.beginnerName = beginnerName;
		this.advancedName = advancedName;
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

}
