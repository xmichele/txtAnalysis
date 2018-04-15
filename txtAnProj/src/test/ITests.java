/**
 * 
 */
package test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashSet;
import org.junit.Test;

import files_analysis.TextFilesAnalysis;

/**
 * @author Michele.LONGOBARDO
 *
 */
public class ITests {
	public final static String IN_testFileRPath = "Resources\\GPS_nmea.log";
	public final static String OUT_testFileRPath = "outSumm.txt";

	@Test
	public void TextFileAnalysis() {
		boolean check = false;
		TextFilesAnalysis txtFAn = new TextFilesAnalysis();
		// execute the scanning
		HashSet<String> excludedWords = new HashSet<String>();
		txtFAn.executeScan(IN_testFileRPath, excludedWords);
		int outWlistLimit = 10;
		//Video print of the scanning result
		String report = txtFAn.getResult(outWlistLimit);
		System.out.println(report);
		try {
			//Write the summary result to text file
			txtFAn.writeResult(OUT_testFileRPath, outWlistLimit);
			check = true;
		} catch (IOException e) {
			System.out.println("IOException: " + e.getMessage());
		}
		assertTrue(check);
	}

}
