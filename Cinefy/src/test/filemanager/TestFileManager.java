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
}
