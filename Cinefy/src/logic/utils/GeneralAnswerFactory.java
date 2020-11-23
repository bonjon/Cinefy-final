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
			messageColleague = "I also suggest you to contact the advanced user "+
					rb.getColleagueName()+" because of "+rb.getReasonChoice()+"\n"+"\n";
			answer = answer +messageColleague;
		}
		
		if (rb.isAResourceSuggested()==true) {
			messageResource = "I also suggest you to look at the following the web contents:"+"\n";
			String wikiRow="Wikipedia "+rb.getWikiLink()+"\n";
			String youtubeRow="YouTube "+rb.getYoutubeLink();
			messageResource=messageResource+wikiRow+youtubeRow;
			answer = answer + messageResource;
		}
		
		
		return answer;
		
	
	}
	
	

}
