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
	
	// Utilizzo un pattern Builder poichè l'entity Film ha 8 parametri quindi un numero di parametri abbastanza elevato
	
	public static class FilmBuilder {
		
		private int filmId;
		private String titolo;
		private String url;
		private String regista;
		private String attore;
		private int anno;
		private String nazione;
		private String genere;
		
		public FilmBuilder(int filmId, String titolo, String url, String regista) {
			this.filmId = filmId;
			this.titolo = titolo;
			this.url = url;
			this.regista = regista;
		}
		
		public FilmBuilder setAttore(String attore) {
			this.attore = attore;
			return this;
		}
		
		public FilmBuilder setAnno(int anno) {
			this.anno = anno;
			return this;
		}
		
		public FilmBuilder setNazione(String nazione) {
			this.nazione = nazione;
			return this;
		}
		
		public FilmBuilder setGenere(String genere) {
			this.genere = genere;
			return this;
		}
		
		public Film build() {
			return new Film(this);
		}
	}
	
	private Film(FilmBuilder builder) {
		this.filmId = builder.filmId;
		this.titolo = builder.titolo;
		this.url = builder.url;
		this.regista = builder.regista;
		this.attore = builder.attore;
		this.anno = builder.anno;
		this.nazione = builder.nazione;
		this.genere = builder.genere;
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
