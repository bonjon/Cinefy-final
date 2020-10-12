package logic.controllers;

import java.sql.SQLException;
import java.util.List;

import logic.bean.PlaylistBean;
import logic.dao.PlaylistDAO;
import logic.entities.Playlist;
import logic.exceptions.PlaylistNotFoundException;
import logic.utils.Controller;


/*
 * Classe ViewPlaylistsController che avrà il compito di
 * coordinare il comportamento del caso d'uso
 * View playlists.
 */

public class ViewPlaylistsController extends Controller {

	public List<PlaylistBean> getLeaderBoard() throws PlaylistNotFoundException, SQLException {
		PlaylistDAO pd = new PlaylistDAO();
		List<Playlist> lpb = pd.leaderBoardPl();
		return this.convertPlaylistList(lpb);
	}

	public PlaylistBean getPlaylist(String p) throws SQLException, PlaylistNotFoundException {
		PlaylistDAO pd = new PlaylistDAO();
		Playlist pp = pd.selectPlaylistByName(p);
		return this.convert(pp);
	}

	public List<PlaylistBean> getPlaylistByAd(String user) throws SQLException, PlaylistNotFoundException {
		PlaylistDAO pd = new PlaylistDAO();
		List<Playlist> lp = pd.selectPlaylistByUsername(user);
		return this.convertPlaylistList(lp);
	}	
}
