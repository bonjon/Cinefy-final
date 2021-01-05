package logic.controllers;

import java.sql.SQLException;

import logic.bean.GeneralUserBean;
import logic.dao.GeneralUserDAO;
import logic.entities.GeneralUser;
import logic.exceptions.FieldEmptyException;
import logic.utils.ExceptionInfo;

public class LoginController {
	
	ExceptionInfo ei= new ExceptionInfo();

	public GeneralUserBean login(GeneralUserBean userBean) throws 
	FieldEmptyException, ClassNotFoundException {
		boolean beanCheck=false; //booleano: true se uno dei campi della bean è vuoto, false altrimenti.
		

		if (userBean.getUsername().equals("")) {
			ei.setEmptyUsername(true);
			beanCheck=true;
		}
		if (userBean.getPassword().equals("")) {
			ei.setEmptyPassword(true);
			beanCheck=true;
		}
		if(beanCheck) {
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
	
	public ExceptionInfo getExceptionInfo() {
		return ei;
	}
}