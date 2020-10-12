package logic.bean;

import java.io.Serializable;

/*
 * Classe bean di Film che sarà interposta tra la View e la Control
 */

public class FilmBean implements Serializable {
	
	static final long serialVersionUID = 42L;
	private String filmId;
	private String titolo;
	private String url;
	private String regista;
	private String attore;
	private String anno;
	private String nazione;
	private String genere;

	public String getFilmId() {
		return filmId;
	}

	public void setFilmId(int filmId) {
		this.filmId = "" + filmId;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRegista() {
		return regista;
	}

	public void setRegista(String regista) {
		this.regista = regista;
	}

	public String getAttore() {
		return attore;
	}

	public void setAttore(String attore) {
		this.attore = attore;
	}

	public String getAnno() {
		return anno;
	}

	public void setAnno(int anno) {
		this.anno = "" + anno;
	}

	public String getNazione() {
		return nazione;
	}

	public void setNazione(String nazione) {
		this.nazione = nazione;
	}

	public String getGenere() {
		return genere;
	}

	public void setGenere(String genere) {
		this.genere = genere;
	}
}
