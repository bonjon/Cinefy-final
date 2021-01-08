package test.filemanager;

/*
 * Author: Jacopo Onorati
 */

import org.junit.Test;
import static org.junit.Assert.*;

import logic.utils.FileManager;

public class TestFileManager {
	
	@Test
	public void testGenerateNewFileName() {
		//caso in cui la stringa formata username + file name risulta minore di 45 caratteri
		
		String fileName;
		String username;
		String attended;
		String output;
		fileName = "File";
		username = "Name";
		output = FileManager.generateNewFileName(fileName, username);
		attended = "NameFile";
		assertEquals(attended, output);
	}
	
	@Test
	public void testGenerateLongNewFileName() {
		//caso in cui la stringa formata username + file name risulta maggiore di 45 caratteri
		
		String fileN;
		String userN;
		String attended;
		String output;
		fileN = "Very long and complicated file name with many spaces. This file name has more than 45 characters.jpg";
		userN = "Name";
		output = FileManager.generateNewFileName(fileN, userN);
		attended = "NameVery long and complicated file name .jpg";
		assertEquals(attended, output);
	}
}
