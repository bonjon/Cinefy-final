package test.addquestion;

import java.sql.SQLException;

/*
 * Author: Giovanni Pica
 */

import org.junit.Test;

import logic.bean.DomandaBean;
import logic.controllers.AskForQuestionsController;
import logic.exceptions.FieldEmptyException;
import logic.exceptions.FieldTooLongException;

public class TestAddQuestion {

	private static final String TEST = "test";

	@Test(expected = FieldTooLongException.class)
	public void testAddQuestionTooLong() throws FieldTooLongException, SQLException, FieldEmptyException {
		AskForQuestionsController afc = new AskForQuestionsController();
		StringBuilder bld = new StringBuilder();
		for (int i = 0; i < 200; i++) {
			bld.append("a");
		}
		DomandaBean db = new DomandaBean();
		db.setId(42);
		db.setAdvancedName(TEST);
		db.setBeginnerName(TEST);
		db.setContenuto(bld.toString());
		afc.makeQuestion(db.getContenuto(), db.getBeginnerName(), db.getAdvancedName());
	}
}
