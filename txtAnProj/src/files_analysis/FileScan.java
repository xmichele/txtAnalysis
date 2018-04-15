/**
 * 
 */
package files_analysis;

import java.io.File;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;

/**
 * @author Michele.LONGOBARDO
 *
 */
public class FileScan {
	private static final String defaulSplPattern = "\\W+";
	private String splPattern;

	/**
	 * Constructor
	 * @param splPattern
	 * @throws Exception
	 */
	public FileScan(String splPattern) throws Exception {
		this.splPattern = checkSplPattern(splPattern);
	}

	/**
	 * Constructor
	 * @throws Exception
	 */
	public FileScan() throws Exception {
		this(defaulSplPattern);
	}

	/**
	 * Scan the input file updating the summary Infos and according to the file
	 * extension and excluded words filter
	 * 
	 * @param f
	 * @param hmap
	 * @param excludedWords
	 * @throws Exception
	 */
	void scanFile(final File f, final SummaryInfos sInfos, final HashSet<String> excludedWords) throws Exception {
		// System.out.println("f " + f.toString());
		if (checkFilefilter(f, new String[] { "txt", "log" })) {
			System.out.println(f.toString() + " file scanning ... ");
			List<String> ftext = Files.readAllLines(f.toPath());
			for (String line : ftext) {
				sInfos.incrTotalLines();
				// System.out.println("line " + line);
				String[] words = splittingExec(line);
				// System.out.println("words " + words.toString());
				Integer winfo_cnt = null;
				for (String word : words) {
					if (checkWordInterest(word, excludedWords)) {
						winfo_cnt = sInfos.incrWInfo(word);
						if (winfo_cnt == null) {
							sInfos.addNewWInfo(word);
						}
						sInfos.incrTotalWincr();
					} else {
						sInfos.incrTotalWdiscarded();
					}
				} // END FOR words
			} // END FOR lines
		} else
			throw new FSException(FSExcEnum.EXC_FILE_EXTENSION);
	}// end fcn

	/**
	 * Check if the input file is intersting according to list of allowed
	 * extensions
	 * 
	 * @param file
	 * @param str
	 * @return
	 */
	private boolean checkFilefilter(final File file, String[] allowedExt) throws FSException {
		boolean check = file.isFile();
		if (check) {
			String fileExt = getFileExtension(file);
			// System.out.println("fileExt " + fileExt );
			for (String ext : allowedExt) {
				if (fileExt.equals(ext))
					return true;
			}
		} else
			throw new FSException(FSExcEnum.NOT_A_FILE);
		return false;
	}

	/**
	 * Get file extension
	 * 
	 * @param file
	 * @return
	 */
	public static String getFileExtension(final File file) {
		String name = file.getName();
		try {
			return name.substring(name.lastIndexOf(".") + 1);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * Split a string (i.e. an acquired text file line) according to words or
	 * patterns
	 * 
	 * @param str
	 * @return
	 */
	String[] splittingExec(String str) {
		return str.split(this.splPattern);
	}

	/**
	 * Check the integrity of the desired string split pattern
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	private String checkSplPattern(String str) throws Exception {
		return str;//pattern check logics could be added
	}

	/**
	 * Check if the input word respect base words filter and is not present in
	 * excluded words list
	 * 
	 * @param word
	 * @param excludedWords
	 * @return
	 */
	boolean checkWordInterest(final String word, HashSet<String> excludedWords) {
		//  some personalizations could be be enabled for user
		return (word != null && word.toCharArray().length >= 2 && !word.matches("\\d+") && !excludedWords.contains(word));
	}

}
