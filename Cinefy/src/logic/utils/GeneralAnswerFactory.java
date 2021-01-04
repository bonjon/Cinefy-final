package logic.utils;

import logic.bean.RispostaBean;

public class GeneralAnswerFactory implements AnswerAutomationFactory{

	String header;
	String messageColleague;
	String messageResource;
	String answer;
	
	
	public GeneralAnswerFactory () {

		this.header = "||GENERAL ANSWER||";
		
		
	}
	
	@Override
	public String answerCreation(RispostaBean rb) {
		
		answer = header+"\n"+"\n"+rb.getContenuto()+"\n"+"\n";
		
		
		if (rb.isAColleagueSuggested()==true) {
			String reasonChoice = rb.getReasonChoice();
			String firstLetter = reasonChoice.substring(0,1);
			String newReasonChoice = reasonChoice.replaceFirst(firstLetter,firstLetter.toLowerCase());
			
			messageColleague = "I also suggest you to contact the advanced user "+
					rb.getColleagueName()+" because of "+newReasonChoice+"."+"\n"+"\n";
			answer = answer +messageColleague;
		}
		
		boolean resSugg = rb.isAResourceSuggested();
		if (resSugg==true) {
			
			messageResource = "I advise you to look at the following web contents:"+"\n";
			
			if (rb.getWikiLink().isEmpty()==false) {
				
				String wikiRow="Wikipedia - "+rb.getWikiLink()+"\n";
				messageResource=messageResource+wikiRow;
			}
			
			if (rb.getYoutubeLink().isEmpty()==false) {
			
				String youtubeRow = "YouTube - " +rb.getYoutubeLink()+"\n"+"\n"+"\n";
				messageResource=messageResource+youtubeRow;
			}
			
			
			answer = answer + messageResource;
		}
		
		
		return answer;
		
	
	}
	
	

}
