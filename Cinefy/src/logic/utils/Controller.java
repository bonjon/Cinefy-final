package logic.utils;

import java.util.ArrayList;
import java.util.List;

import logic.bean.AdvancedUserBean;
import logic.bean.DomandaBean;
import logic.bean.FilmBean;
import logic.bean.PlaylistBean;
import logic.bean.RispostaBean;
import logic.entities.AdvancedUser;
import logic.entities.Domanda;
import logic.entities.Film;
import logic.entities.Playlist;
import logic.entities.Risposta;

/*
 * Controller che serve per la conversione di entity in bean
 */

public abstract class Controller {

	protected FilmBean convert(Film fm) {
		FilmBean fb = new FilmBean();
		fb.setFilmId(fm.getFilmId());
		fb.setTitolo(fm.getTitolo());
		fb.setAttore(fm.getAttore());
		fb.setUrl(fm.getUrl());
		fb.setRegista(fm.getRegista());
		fb.setAnno(fm.getAnno());
		fb.setGenere(fm.getGenere());
		fb.setNazione(fm.getNazione());
		fb.setGenere(fm.getGenere());
		return fb;
	}

	protected List<FilmBean> convertFilmList(List<Film> l) {
		List<FilmBean> fbl = new ArrayList<>();
		for (int i = 0; i < l.size(); i++) {
			Film fm = l.get(i);
			FilmBean fb = this.convert(fm);
			fbl.add(fb);
		}
		return fbl;
	}

	protected AdvancedUserBean convert(AdvancedUser au) {
		AdvancedUserBean aub = new AdvancedUserBean();
		aub.setBio(au.getBio());
		aub.setUsername(au.getUsername());
		aub.setPassword(au.getPassword());
		aub.setProfession(au.getProfession());
		aub.setProfilePic(au.getProfilePic());
		aub.setRole(au.getRole());
		aub.setVoto(au.getVoto());
		aub.setNumeroVoti(au.getNumeroVoti());
		aub.setTokens(au.getTokens());
		return aub;
	}

	protected List<AdvancedUserBean> convertAdvancedList(List<AdvancedUser> lau) {
		List<AdvancedUserBean> laub = new ArrayList<>();
		for (int i = 0; i < lau.size(); i++) {
			AdvancedUser au = lau.get(i);
			AdvancedUserBean aub = this.convert(au);
			laub.add(aub);
		}
		return laub;
	}

	protected List<DomandaBean> convertQuestionList(List<Domanda> ld) {
		List<DomandaBean> ldb = new ArrayList<>();
		for (int i = 0; i < ld.size(); i++) {
			Domanda d = ld.get(i);
			DomandaBean db = this.convert(d);
			ldb.add(db);
		}
		return ldb;
	}
	
	protected List<RispostaBean> convertAnswerList(List<Risposta> ld) {
		List<RispostaBean> ldb = new ArrayList<>();
		for (int i = 0; i < ld.size(); i++) {
			Risposta d = ld.get(i);
			RispostaBean db = this.convert(d);
			ldb.add(db);
		}
		return ldb;
	}

	protected DomandaBean convert(Domanda d) {
		DomandaBean db = new DomandaBean();
		db.setId(d.getId());
		db.setAdvancedName(d.getAdvancedName());
		db.setBeginnerName(d.getBeginnerName());
		db.setContenuto(d.getContenuto());
		return db;
	}

	protected PlaylistBean convert(Playlist p) {
		PlaylistBean pb = new PlaylistBean();
		pb.setId(p.getId());
		pb.setAdvancedName(p.getAdvancedName());
		pb.setDate(p.getDate());
		pb.setName(p.getName());
		pb.setNumeroVoti(p.getNumeroVoti());
		pb.setPlaylistPic(p.getPlaylistPic());
		pb.setVoto(p.getVoto());
		return pb;
	}

	protected List<PlaylistBean> convertPlaylistList(List<Playlist> lp) {
		List<PlaylistBean> lpb = new ArrayList<>();
		for (int i = 0; i < lp.size(); i++) {
			Playlist p = lp.get(i);
			PlaylistBean pb = this.convert(p);
			lpb.add(pb);
		}
		return lpb;
	}

	protected RispostaBean convert(Risposta r) {
		RispostaBean rb = new RispostaBean();
		rb.setAdvancedName(r.getAdvancedName());
		rb.setBeginnerName(r.getBeginnerName());
		rb.setContenuto(r.getContenuto());
		rb.setId(r.getId());
		rb.setIdDomanda(r.getIdDomanda());
		return rb;
	}
}
