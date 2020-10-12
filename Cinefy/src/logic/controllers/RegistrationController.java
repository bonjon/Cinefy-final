package logic.controllers;

import java.sql.SQLException;

import logic.bean.AdvancedUserBean;

import logic.bean.BeginnerUserBean;
import logic.bean.GeneralUserBean;
import logic.dao.AdvancedUserDAO;

import logic.dao.BeginnerUserDAO;
import logic.exceptions.FieldEmptyException;
import logic.exceptions.FieldTooLongException;


public class RegistrationController {
	
	public Boolean createBeginnerUser(BeginnerUserBean bub) throws FieldEmptyException, FieldTooLongException {
		boolean beanCheck=false; //booleano: true se uno dei campi della bean è vuoto, false altrimenti.
		
		System.out.println("prima del controllo");
		System.out.println(FieldEmptyException.EMPTYROLE);
		System.out.println(bub.getProfilePic());
		
		if (bub.getUsername().equals("")) {
			FieldEmptyException.EMPTYUSERNAME = true;
			beanCheck=true;
		}
		if (bub.getPassword().equals("")) {
			FieldEmptyException.EMPTYPASSWORD = true;
			beanCheck=true;
		}
		if (bub.getRole()=="noSelRole") {
			FieldEmptyException.EMPTYROLE = true;
			System.out.println("dopo del controllo");
			System.out.println(FieldEmptyException.EMPTYROLE);
			beanCheck=true;
		}
		
		if(beanCheck==true) {
			beanCheck=false;
			throw new FieldEmptyException("This field cannot be empty");
		}
		
		boolean lengthControl = fieldTooLongControls(bub,bub.getBio());
		if(lengthControl==true) {
			throw new FieldTooLongException("You exceeded the max number of characters for this field");
		}

		BeginnerUserDAO bud = new BeginnerUserDAO();
		
		try {
			System.out.println(bub.getProfilePic());
			return bud.createBeginnerUser(bub.getUsername(), bub.getPassword(),bub.getBio(),bub.getProfilePic());
		} catch (SQLException e) {
			return Boolean.FALSE;
		} 
	}

	public Boolean createAdvancedUser(AdvancedUserBean aub)throws FieldEmptyException, FieldTooLongException {
		boolean beanCheck=false;
		
		if (aub.getUsername().equals("")) {
			FieldEmptyException.EMPTYUSERNAME = true;
			beanCheck=true;
		}
		if (aub.getPassword().equals("")) {
			FieldEmptyException.EMPTYPASSWORD = true;
			beanCheck=true;
		}
		if (aub.getProfession()==null) {
			FieldEmptyException.EMPTYPROFESSION = true;
			beanCheck=true;
		}
		
		if(beanCheck==true) {
			beanCheck=false;
			throw new FieldEmptyException("This field cannot be empty");
		}
		
		boolean lengthControl = fieldTooLongControls(aub,aub.getBio());
		if(lengthControl==true) {
			throw new FieldTooLongException("Too many characters for this field");
		}
		
		
	
		AdvancedUserDAO aud = new AdvancedUserDAO();
		try {
			return aud.createAdvancedUser(aub.getUsername(), aub.getPassword(), aub.getProfession(),aub.getBio(),aub.getProfilePic());
		} catch (SQLException e) {
			return Boolean.FALSE;
		}
	}

	public boolean fieldTooLongControls(GeneralUserBean gub,String bio) {
		int userLength = 16;
		int passwordLength = 32;
		int bioLength = 200;
		boolean beanLengthCheck = false; //booleano: true se uno dei campi della bean è troppo lungo, false altrimenti.
		
		if(gub.getUsername().length()>userLength) {
			FieldTooLongException.USERTOOLONG=true;
			beanLengthCheck = true;
		}
		if(gub.getPassword().length()>passwordLength) {
			FieldTooLongException.PASSTOOLONG=true;
			beanLengthCheck = true;
		}
		if(bio.length()>bioLength) {
			FieldTooLongException.BIOTOOLONG=true;
			beanLengthCheck = true;
		}
		
		if (beanLengthCheck == true) { 
			return true;
		}
		else {
			return false;
		}
		
	}
	
}

