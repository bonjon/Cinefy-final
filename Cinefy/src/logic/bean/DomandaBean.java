package logic.bean;

import java.io.Serializable;

/*
 * Classe bean di Domanda che sarà interposta tra view e control
 */

public class DomandaBean implements Serializable {
	
	static final long serialVersionUID = 42L;
	private String id;
	private String contenuto;
	private String beginnerName;
	private String advancedName;
	

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

	public String getBeginnerName() {
		return beginnerName;
	}

	public void setBeginnerName(String beginnerName) {
		this.beginnerName = beginnerName;
	}

	public String getAdvancedName() {
		return advancedName;
	}

	public void setAdvancedName(String advancedName) {
		this.advancedName = advancedName;
	}
	
	

}
