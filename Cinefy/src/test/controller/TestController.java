package test.controller;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Test;

import logic.bean.DomandaBean;
import logic.bean.RispostaBean;
import logic.controllers.AnswerQuestionsController;
import logic.controllers.AskForQuestionsController;
import logic.entities.Domanda;
import logic.entities.Risposta;
import logic.utils.Controller;

public class TestController {

	private static final String TEST = "test";

	/*
	 * Author: Giovanni Pica
	 */
	
	@Test
	public void testDomandaBeanConvert() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		DomandaBean db = new DomandaBean();
		db.setId(42);
		db.setAdvancedName(TEST);
		db.setBeginnerName(TEST);
		db.setContenuto(TEST);
		Domanda d = new Domanda(42, TEST, TEST, TEST);
		Method method = Controller.class.getDeclaredMethod("convert", Domanda.class);
		method.setAccessible(true);
		DomandaBean db2 = (DomandaBean) method.invoke(new AskForQuestionsController(), d);
		assertTrue(EqualsBuilder.reflectionEquals(db, db2));
	}

	/*
	 * Author: Jacopo Onorati
	 */
	
	@Test
	public void rispostaBeanConverter() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		RispostaBean attended = new RispostaBean();
		attended.setId(40);
		attended.setAdvancedName(TEST);
		attended.setBeginnerName(TEST);
		attended.setContenuto(TEST);
		attended.setIdDomanda(41);
		Risposta r = new Risposta(40, TEST, TEST, TEST, 41);
		Method method = Controller.class.getDeclaredMethod("convert", Risposta.class);
		method.setAccessible(true);
		RispostaBean output = (RispostaBean) method.invoke(new AnswerQuestionsController(), r);
		assertTrue(EqualsBuilder.reflectionEquals(attended, output));
	}
}
