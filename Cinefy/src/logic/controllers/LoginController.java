package logic.controllers;

import java.sql.SQLException;

import logic.bean.GeneralUserBean;
import logic.dao.GeneralUserDAO;
import logic.entities.GeneralUser;
import logic.exceptions.FieldEmptyException;

public class LoginController {

	public GeneralUserBean login(GeneralUserBean userBean) throws 
	FieldEmptyException, ClassNotFoundException {
		boolean beanCheck=false; //booleano: true se uno dei campi della bean è vuoto, false altrimenti.
		

		if (userBean.getUsername().equals("")) {
			FieldEmptyException.EMPTYUSERNAME = true;
			beanCheck=true;
		}
		if (userBean.getPassword().equals("")) {
			FieldEmptyException.EMPTYPASSWORD = true;
			beanCheck=true;
		}
		if(beanCheck==true) {
			beanCheck=false;
			throw new FieldEmptyException("This field cannot be empty");
		}
		
		GeneralUserDAO gud = new GeneralUserDAO();
		GeneralUser result;
		try {
			result = gud.findUser(userBean.getUsername(), userBean.getPassword());
			GeneralUserBean gu = new GeneralUserBean();
			
			gu.setUsername(result.getUsername());
			gu.setPassword(result.getPassword());
			gu.setRole(result.getRole());
			return gu;
		}
		catch(SQLException | NullPointerException e) {
			return null;
		}
	}
}