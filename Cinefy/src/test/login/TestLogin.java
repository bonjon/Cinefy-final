package test.login;

/*
 * Author: Jacopo Onorati
 */


import static org.junit.Assert.assertEquals;

import org.junit.Test;

import logic.bean.GeneralUserBean;
import logic.controllers.LoginController;
import logic.exceptions.FieldEmptyException;

public class TestLogin {

	@Test
	public void testUsernameEmptyLogin() throws ClassNotFoundException {
		LoginController lc = new LoginController();
		String output;
		String attended;
		String username;
		String pass;
		GeneralUserBean gub = new GeneralUserBean();
		output = "";
		attended = "This field cannot be empty";
		username = "";
		pass = "password";
		gub.setUsername(username);
		gub.setPassword(pass);
		try {
			lc.login(gub);
		} catch (FieldEmptyException e) {
			output = e.getMessage();
		} finally {
			assertEquals(attended, output);
		}
	}
}
