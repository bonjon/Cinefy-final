package logic.entities;

/*
 * Classe entity che rappresenta la tabella Film
 */

public class Film {

	private int filmId;
	private String titolo;
	private String url;
	private String regista;
	private String attore;
	private int anno;
	private String nazione;
	private String genere;

	public Film(int filmId) {
		this.filmId = filmId;
	}

	public Film(int filmId, String titolo, String url, String regista, String attore, int anno, String nazione,
			String genere) {
		this.filmId = filmId;
		this.titolo = titolo;
		this.url = url;
		this.regista = regista;
		this.attore = attore;
		this.anno = anno;
		this.nazione = nazione;
		this.genere = genere;
	}

	public int getFilmId() {
		return filmId;
	}

	public String getTitolo() {
		return titolo;
	}

	public String getUrl() {
		return url;
	}

	public String getAttore() {
		return attore;
	}

	public int getAnno() {
		return anno;
	}

	public String getNazione() {
		return nazione;
	}

	public String getGenere() {
		return genere;
	}

	public String getRegista() {
		return regista;
	}

}
