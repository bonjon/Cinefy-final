package logic.controllers;

import java.sql.SQLException;

import logic.bean.AdvancedUserBean;
import logic.bean.BeginnerUserBean;
import logic.dao.GeneralUserDAO;
import logic.entities.AdvancedUser;
import logic.entities.BeginnerUser;
import logic.utils.Controller;

/*
 * Classe ProfileController che appunto è il controller di
 * Profile.
 */

public class ProfileController extends Controller {

	public BeginnerUserBean getUser(String username, String role) {
		GeneralUserDAO gud = new GeneralUserDAO();
		BeginnerUserBean bub = new BeginnerUserBean();
		try {
			BeginnerUser gu = gud.findByName(username, role);
			bub.setUsername(gu.getUsername());
			bub.setPassword(gu.getPassword());
			bub.setBio(gu.getBio());
			bub.setProfilePic(gu.getProfilePic());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bub;
	}

	public AdvancedUserBean getUser2(String username, String role) {
		GeneralUserDAO gud = new GeneralUserDAO();
		AdvancedUserBean aub = new AdvancedUserBean();
		try {
			AdvancedUser gu = gud.findByName2(username, role);
			aub.setUsername(gu.getUsername());
			aub.setPassword(gu.getPassword());
			aub.setBio(gu.getBio());
			aub.setProfilePic(gu.getProfilePic());
			aub.setNumeroVoti(gu.getNumeroVoti());
			aub.setProfession(gu.getProfession());
			aub.setTokens(gu.getTokens());
			aub.setVoto(gu.getVoto());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return aub;
	}

	
}
