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
		this.explanationHeader = "I adviced you this film because of the reason below.\n";
		this.titleHeader = "Title: ";
		this.genreHeader = "Genre: ";
		
		
		
	}

	@Override
	public String answerCreation(RispostaBean rb) {
		String result="";
		String row1,row2,row3,row1bis;
		
		partecipantHeader = rb.getProfession()+" to focus on: ";
		title = rb.getFilm();
		partecipant = rb.getPartecipant();
		genre = rb.getGenre();
		
		explanation = rb.getExplanation();
		
		row1 = header+"\n"+"\n";
		row1bis = "I suggest you to watch the following film:\n";
		row2 = titleHeader+title+"\n"+partecipantHeader+partecipant+"\n"+genreHeader+genre+"\n"+"\n";
		row3 = explanationHeader+explanation+"\n"+"\n"+"\n";
		result = row1+row1bis+row2+row3;
		
		return result;
	}

}
