package logic.controllers;

import java.sql.SQLException;

import logic.bean.AdvancedUserBean;

import logic.bean.BeginnerUserBean;
import logic.bean.GeneralUserBean;
import logic.dao.AdvancedUserDAO;

import logic.dao.BeginnerUserDAO;
import logic.exceptions.FieldEmptyException;
import logic.exceptions.FieldTooLongException;
import logic.utils.ExceptionInfo;

public class RegistrationController {
	
	ExceptionInfo ei = new ExceptionInfo();
	
	public void createGeneralUser(GeneralUserBean gub) throws FieldEmptyException {
		 
		/* Caso in cui il ruolo non è stato scelto, quindi è come se si volesse creare un utente senza ruolo.
		 * Dato che questa opzione non è ammessa, questo metodo causerà sempre un' eccezione di default.
		 */

		if (gub.getUsername().equals("")) {
			ei.setEmptyUsername(true);
		}
		
		if (gub.getPassword().equals("")) {
			ei.setEmptyPassword(true);
		}
		
		if (gub.getRole().equals("")) {
			ei.setEmptyRole(true);
		}
		
		throw new FieldEmptyException("This field cannot be empty");
	}

	public Boolean createBeginnerUser(BeginnerUserBean bub)
			throws FieldEmptyException, FieldTooLongException, ClassNotFoundException {
		boolean beanCheck = false; // booleano: true se uno dei campi della bean è vuoto, false altrimenti.
		
		
		if (bub.getUsername().equals("")) {
			ei.setEmptyUsername(true);
			beanCheck = true;
		}
		if (bub.getPassword().equals("")) {
			ei.setEmptyPassword(true);
			beanCheck = true;
		}
		

		if (beanCheck) {
			throw new FieldEmptyException("This field cannot be empty");
		}

		boolean lengthControl = fieldTooLongControls(bub, bub.getBio());
		if (lengthControl) {
			throw new FieldTooLongException("Too many characters for this field");
		}

		BeginnerUserDAO bud = new BeginnerUserDAO();

		try {

			return bud.createBeginnerUser(bub.getUsername(), bub.getPassword(), bub.getBio(), bub.getProfilePic());
		} catch (SQLException e) {
			return Boolean.FALSE;
		}
	}

	public Boolean createAdvancedUser(AdvancedUserBean aub)
			throws FieldEmptyException, FieldTooLongException, ClassNotFoundException {
		boolean beanCheck = false;

		if (aub.getUsername().equals("")) {
			ei.setEmptyUsername(true);
			beanCheck = true;
		}
		if (aub.getPassword().equals("")) {
			ei.setEmptyPassword(true);
			beanCheck = true;
		}
		if (aub.getProfession() == null) {
			ei.setEmptyProfession(true);
			beanCheck = true;
		}

		if (beanCheck) {
			throw new FieldEmptyException("This field cannot be empty");
		}

		boolean lengthControl = fieldTooLongControls(aub, aub.getBio());
		if (lengthControl) {
			throw new FieldTooLongException("Too many characters for this field");
		}

		AdvancedUserDAO aud = new AdvancedUserDAO();
		try {
			return aud.createAdvancedUser(aub.getUsername(), aub.getPassword(), aub.getProfession(), aub.getBio(),
					aub.getProfilePic());
		} catch (SQLException e) {
			return Boolean.FALSE;
		}
	}

	public boolean fieldTooLongControls(GeneralUserBean gub, String bio) {
		int userLength = 16;
		int passwordLength = 32;
		int bioLength = 200;
		boolean beanLengthCheck = false; // booleano: true se uno dei campi della bean è troppo lungo, false altrimenti.

		if (gub.getUsername().length() > userLength) {
			ei.setTooLongUser(true);
			beanLengthCheck = true;
		}
		if (gub.getPassword().length() > passwordLength) {
			ei.setTooLongPass(true);
			beanLengthCheck = true;
		}
		if (bio.length() > bioLength) {
			ei.setTooLongBio(true);
			beanLengthCheck = true;
		}

		return beanLengthCheck;

	}
	
	public ExceptionInfo getExceptionInfo() {
		return ei;
	}

}
