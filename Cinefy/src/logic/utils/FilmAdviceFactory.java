package logic.utils;


import logic.bean.RispostaBean;

public class FilmAdviceFactory implements AnswerAutomationFactory {

	String header;
	String explanationHeader;
	String titleHeader;
	String partecipantHeader;
	String genreHeader;

	String title, genre, partecipant,explanation;
	
	
	public FilmAdviceFactory() {
		this.header = "||FILM ADVICE||";
		this.explanationHeader = "EXPLANATION: ";
		this.titleHeader = "Title: ";
		this.genreHeader = "Genre: ";
		
		
		
	}

	@Override
	public String answerCreation(RispostaBean rb) {
		String result="";
		String row1,row2,row3;
		
		partecipantHeader = rb.getProfession()+": ";
		title = rb.getFilm();
		partecipant = rb.getPartecipant();
		genre = rb.getGenre();
		
		explanation = rb.getExplanation();
		
		row1 = header+"\n";
		row2 = titleHeader+title+"  "+partecipantHeader+partecipant+"  "+genreHeader+genre+"\n";
		row3 = explanationHeader+explanation;
		result = row1+row2+row3;
		
		return result;
	}

}
