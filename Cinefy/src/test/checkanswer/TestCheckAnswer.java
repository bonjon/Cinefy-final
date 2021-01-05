package test.checkanswer;

import java.sql.SQLException;

/*
 * Author: Giovanni Pica
 */

import org.junit.Test;
import static org.junit.Assert.*;

import logic.bean.DomandaBean;
import logic.bean.GeneralUserBean;
import logic.controllers.AskForQuestionsController;

public class TestCheckAnswer {

	private static final String TEST = "test";
	
	@Test
	public void testCheckAnswer() throws NumberFormatException, SQLException, ClassNotFoundException {
		AskForQuestionsController afc = new AskForQuestionsController();
		GeneralUserBean gub = new GeneralUserBean();
		gub.setUsername(TEST);
		DomandaBean db = new DomandaBean();
		db.setId(42);
		boolean result = afc.checkAnswer(gub.getUsername(), db.getId());
		assertEquals(false, result);
	}
}
