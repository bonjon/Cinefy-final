package logic.utils;

import java.io.File;

/*
 * Classe file manager che serve a gestire i path per le
 * immagini del profilo o di una playlist.
 */

public class FileManager {

    private static String cinefy = "cinefy.git";
	//private static String cinefy = "cinefy";
	public static final String PROJECT = System.getProperty("user.home") + File.separator + cinefy + File.separator
			+ "trunk" + File.separator + "Cinefy" + File.separator + "WebContent" + File.separator + "img"
			+ File.separator;
	public static final String PROFILE = PROJECT + "profilePictures";
	public static final String PLAYLISTS = PROJECT + "playlistPictures";
	public static final String GREEN = PROJECT + "green.png";
	public static final String RED = PROJECT + "red.png";
	public static final String YELLOW = PROJECT + "yellow.png";
	public static final String MARK = PROJECT + "mark.png";

	private FileManager() {

	}

	public static String generateNewFileName(String fileName, String username) {
		String newFileName;
		int maxPicName = 45;

		newFileName = username + fileName;

		if (newFileName.length() > maxPicName) {
			int nameLength = fileName.length();
			String extension = fileName.substring(nameLength - 4, nameLength);
			
			newFileName = username + fileName;
			newFileName = newFileName.substring(0, maxPicName - 5) + extension;
			
		}

		return newFileName;
	}
}
