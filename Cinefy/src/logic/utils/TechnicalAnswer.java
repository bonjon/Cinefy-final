package logic.utils;

import logic.bean.RispostaBean;

public class TechnicalAnswer implements AnswerAutomation{

	String header;
	String advicesHeader;
	
	String answer, advices;
	
	public TechnicalAnswer () {

		this.header = "**TECHNICAL ANSWER**";
		this.advicesHeader = "ADVICES FOR PRACTICING: ";
	}
	
	@Override
	public String answerCreation(RispostaBean rb) {
		String result="";
		int i;
		int dimension = header.length();
		String empty = " ";
		String spacing= "";
		
		answer = rb.getContenuto();
		advices = rb.getExplanation();
		
		for(i=0;i<dimension;i++) {spacing=spacing+empty;};
		result = this.header+answer+"\n"+spacing+advicesHeader+advices;
		return result;
	}
	
	

}
