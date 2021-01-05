package logic.utils;

import logic.bean.GeneralUserBean;

/*
 * Classe SessionUser che si occupa di vedere qual è l'utente
 * che si è appena loggato, naturalmente è stato applicato il
 * pattern Singleton poichè soltanto quell'utente deve essere
 * loggato in quella sessione.
 */

public class SessionUser {

	private GeneralUserBean userSession = null;
	private static SessionUser instance = null;

	private SessionUser() {

	}

	public static SessionUser getInstance() {
		if (instance == null) {
			instance = new SessionUser();
		}
		return instance;
	}

	public void setSession(GeneralUserBean userSession) {
		if (this.userSession == null) {
			this.userSession = userSession;
		}
	}

	public GeneralUserBean getSession() {
		return this.userSession;
	}

	public void closeSession() {
		this.userSession = null;
	}
}
