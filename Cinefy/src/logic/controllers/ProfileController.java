package logic.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import logic.bean.AdvancedUserBean;
import logic.bean.BeginnerUserBean;
import logic.bean.DomandaBean;

import logic.dao.DomandaDAO;
import logic.dao.GeneralUserDAO;
import logic.entities.AdvancedUser;
import logic.entities.BeginnerUser;
import logic.entities.Domanda;
import logic.utils.Controller;

/*
 * Classe ProfileController che appunto è il controller di
 * Profile.
 */

public class ProfileController extends Controller {
	
	private static final Logger logger = Logger.getLogger(ProfileController.class.getName());

	public BeginnerUserBean getUser(String username, String role) throws ClassNotFoundException {
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
			logger.log(Level.WARNING, "db error");
			
		}
		return bub;
	}

	public List<DomandaBean> getQuestions(String beginner, String role) throws SQLException, ClassNotFoundException {
		DomandaDAO dd = new DomandaDAO();
		List<Domanda> ld = dd.getQuestions(beginner, role);
		if (ld == null)
			return Collections.emptyList();
		return this.convertQuestionList(ld);
	}

	public List<AdvancedUserBean> differentAdv(String beginner) throws ClassNotFoundException, SQLException {

		List<DomandaBean> ld;
		int i = 0;

		ld = getQuestions(beginner, "beginner");

		List<AdvancedUserBean> contactedAdv = new ArrayList<>();
		List<AdvancedUserBean> differentAdv = new ArrayList<>();

		while (i < ld.size()) { // prendo tutti gli advanced e li metto in una lista
			AdvancedUserBean advanced = new AdvancedUserBean();

			DomandaBean adv = ld.get(i);
			advanced.setUsername(adv.getAdvancedName());
			contactedAdv.add(advanced);
			i++;

		}

		int y;
		
		if (contactedAdv.isEmpty()) {
			return differentAdv;
		}
		for (y = 0; y < contactedAdv.size(); y++) {
			
			if (differentAdv.isEmpty()) {
				differentAdv.add(contactedAdv.get(y));
			} else {

				if (!differentAdv.contains(contactedAdv.get(y))) {
				
					differentAdv.add(contactedAdv.get(y));

				}
			}
		}

		return differentAdv;
	}

	public AdvancedUserBean getUser2(String username, String role) throws ClassNotFoundException {
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
