package test.controller;

import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Test;

import logic.bean.DomandaBean;
import logic.bean.RispostaBean;
import logic.controllers.AnswerQuestionsController;
import logic.controllers.AskForQuestionsController;
import logic.entities.Domanda;
import logic.entities.Risposta;

public class TestController {

	private static final String TEST = "test";

	/*
	 * Author: Giovanni Pica
	 */

	@Test
	public void testDomandaBeanConvert() {
		DomandaBean db = new DomandaBean();
		db.setId(42);
		db.setAdvancedName(TEST);
		db.setBeginnerName(TEST);
		db.setContenuto(TEST);
		Domanda d = new Domanda(42, TEST, TEST, TEST);
		DomandaBean db2 = new AskForQuestionsController().convert(d);
		assertTrue(EqualsBuilder.reflectionEquals(db, db2));
	}

	/*
	 * Author: Jacopo Onorati
	 */

	@Test
	public void rispostaBeanConverter() {
		RispostaBean attended = new RispostaBean();
		attended.setId(40);
		attended.setAdvancedName(TEST);
		attended.setBeginnerName(TEST);
		attended.setContenuto(TEST);
		attended.setIdDomanda(41);
		Risposta r = new Risposta(40, TEST, TEST, TEST, 41);
		RispostaBean output = new AnswerQuestionsController().convert(r);
		assertTrue(EqualsBuilder.reflectionEquals(attended, output));
	}
}
