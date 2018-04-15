/**
 * 
 */
package files_analysis;

import static org.junit.Assert.assertTrue;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import org.junit.Test;

/**
 * @author Michele.LONGOBARDO
 *
 */
public class BaseTests {
	public final static String IN_testFileRPath = "Resources\\GPS_nmea.log";
	public final static String OUT_testFileRPath = "outSumm.txt";

	@Test
	/**
	 * WordInfo base structure test
	 */
	public void testWordInfo() {
		String keyw = "myword";
		WordInfo winfo = new WordInfo(keyw);
		int incrNum = 500;
		for (int i = 0; i < 500; i++) {
			winfo.incrWCount();
		}
		System.out.println(winfo.getWord() + " word -> c: " + winfo.getCount());
		assertTrue(winfo.getWord().equals(keyw) && winfo.getCount() == (incrNum + 1));
	}

	@Test
	public void testSummaryInfo() {
		boolean check = false;

		try {
			SummaryInfos sum_infos = new SummaryInfos();
			final String firstWord = "myword";
			sum_infos.addNewWInfo(firstWord);
			Integer wcount = sum_infos.incrWInfo(firstWord);
			if (wcount == null || wcount != 2)
				throw new Exception("Add Winfo Exception");
			sum_infos.incrTotalLines();
			sum_infos.incrTotalWdiscarded();
			sum_infos.incrTotalWincr();
			// increments check
			if (sum_infos.getTotalLines() != 1) {
				throw new Exception("total lines value exception!");
			}
			if (sum_infos.getTotalWdiscarded() != 1) {
				throw new Exception("total W discarded value exception!");
			}
			if (sum_infos.getTotalWincr() != 1) {
				throw new Exception("total W increments value exception!");
			}

			int newWordsSize = 15;
			for (int i = 1; i <= newWordsSize; i++) {
				sum_infos.addNewWInfo("myword_" + i);
			}
			int additionalOccurrences = 77;
			String frequent_word = "myword_13";
			for (int i = 1; i <= additionalOccurrences; i++) {
				sum_infos.incrWInfo(frequent_word);
			}
			// short and resize check
			sum_infos.sortByMax();
			final int outWlistLimit = 10;
			System.out.println(sum_infos.getResult(outWlistLimit));
			ArrayList<WordInfo> sortedArr = sum_infos.getWInfoArr(outWlistLimit);
			check = sortedArr.get(0).getCount() == (additionalOccurrences+1);
			check = check && sortedArr.get(0).getWord().equals(frequent_word) && sortedArr.get(1).getWord().equals(firstWord);
			check = check && sortedArr.size() == outWlistLimit;
		} catch (Exception e) {
			check = false;
			System.out.println(e.getMessage());
		}
		assertTrue(check);
	}

	@Test
	public void FileScanTest() {
		FileScan fscan;
		boolean check = false;
		try {
			fscan = new FileScan();
			File file = new File(IN_testFileRPath);
			System.out.println(file.getAbsolutePath());
			HashSet<String> excluded_words = new HashSet<String>();
			excluded_words.add("string");
			excluded_words.add("split");
			fscan.scanFile(file, new SummaryInfos(), excluded_words);
			String[] splArr = fscan.splittingExec("Hi I'm the string to split!! @ 12345.358, 789001;???");
			// special characters and punctuation discarded by Fscan internal str pattern \\W+
			check = (splArr != null && splArr.length == 10);
			int interestWcount = 0;
			boolean filterOK = false;
			for (String str : splArr) {
				filterOK = fscan.checkWordInterest(str, excluded_words);
				System.out.println(str + (filterOK ? " -> OK" : ""));
				if (filterOK && !excluded_words.contains(str)) {
					++interestWcount;
				}
			} // END FOR

			// excluded words with len < 2 or in the specified List and numbersc (\\d+ pattern)
			check = check && interestWcount == 3;
		} catch (Exception e) {
			e.printStackTrace();
			check = false;
		}
		assertTrue(check);
	}

	/**
	 * Test the rejection of not allowed files (i.e. by file extension)
	 */
	@Test
	public void FileScanDiscardTest() {
		FileScan fscan;
		boolean check = false;
		File file = new File("Resources\\newfile.doc");
		try {
			file.createNewFile();
			fscan = new FileScan();
			System.out.println(file.getAbsolutePath());
			fscan.scanFile(file, new SummaryInfos(), new HashSet<String>());
		} catch (FSException e) {
			System.out.println(e.getMessage());
			if (e.getErrorMsg().equals(FSExcEnum.EXC_FILE_EXTENSION.getMsg()))
				check = true; //file to reject correctly identified
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		} finally {
			file.delete();
		}
		assertTrue(check);
	}
}//END CLASS
