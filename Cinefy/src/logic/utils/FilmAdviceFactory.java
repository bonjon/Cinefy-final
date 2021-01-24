package logic.utils;


import logic.bean.RispostaBean;

public class FilmAdviceFactory implements AnswerAutomationFactory {

	String header;
	String explanationHeader;
	String titleHeader;
	String partecipantHeader;
	String genreHeader;

	String title; 
	String genre; 
	String partecipant;
	String explanation;
	
	
	public FilmAdviceFactory() {
		this.header = "||FILM ADVICE||";
		this.explanationHeader = "Let me explain you why I decided to advice you this film.\n";
		this.titleHeader = "Title: ";
		this.genreHeader = "Genre: ";
		
		
		
	}

	@Override
	public String answerCreation(RispostaBean rb) {
		String result="";
		String row1;
		String row2;
		String row3; 
		String row1bis;
		
		partecipantHeader = rb.getProfession()+" whose work I suggest you to focus on watching the film: ";
		title = rb.getFilm();
		partecipant = rb.getPartecipant();
		genre = rb.getGenre();
		
		explanation = rb.getExplanation();
		
		row1 = header+"\n"+"\n";
		row1bis = "I decided to suggest you to watch a film.\n";
		row2 = titleHeader+title+"\n"+partecipantHeader+partecipant+"\n"+genreHeader+genre+"\n"+"\n";
		row3 = explanationHeader+explanation+"\n"+"\n"+"\n";
		result = row1+row1bis+row2+row3;
		
		return result;
	}

}
