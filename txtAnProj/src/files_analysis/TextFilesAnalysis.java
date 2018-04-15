/**
 * 
 */
package files_analysis;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;

/**
 * @author Michele.LONGOBARDO
 *
 */
public class TextFilesAnalysis {
	private SummaryInfos sInfos = new SummaryInfos();
	private String scannedPath = "";
	private HashSet<String> scannedFwithExc = new HashSet<>();
	public static final String default_in_path = "C:\\Users\\Michele.LONGOBARDO\\Desktop";
	public static final String default_out_path = ".\\outSumm.txt";
	public static final boolean overwriteOLD_outF = true;
	public static final int sortedWListOutputLimit = 10; 
	
	/**
	 * Use case
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String in_path = default_in_path;
		String out_path = default_out_path;
		if (args != null) {
			int args_size = args.length;
			if (args_size >= 2) {
				in_path = args[0];
				out_path = args[1];
			}
		}
		final HashSet<String> excludedWords = new HashSet<>();
		// in a future release user could specify excluded words (i.e. input by cmd line)
		TextFilesAnalysis txtAn = new TextFilesAnalysis();
		txtAn.executeScan(in_path, excludedWords);
		String report = txtAn.getResult(sortedWListOutputLimit);
		System.out.println(report);
		try {
			txtAn.writeResult(out_path, sortedWListOutputLimit);
		} catch (IOException e) {
			System.out.println("IOException: " + e.getMessage());
		}
	} // end main

	/**
	 * Execute the scanning according to the input file or folder path and
	 * passing to the scanFile the list of words to exclude in the text search
	 * 
	 * @param mother_path_str
	 * @param excludedWords
	 */
	public synchronized void executeScan(final String mother_path_str, final HashSet<String> excludedWords) {
		scannedPath = mother_path_str;
		scannedFwithExc.clear();
		sInfos = new SummaryInfos();
		File f = new File(mother_path_str);
		if (f.isFile()) {
			System.out.println("f " + f.toString());
			scanFile(f, sInfos, excludedWords);
		} else if (f.isDirectory()) {
			//can be expanded with the multi-depthLevel scanning feature
			File[] files = f.listFiles();
			for (File file : files) {
				scanFile(file, sInfos, excludedWords);
			}
		}
		// System.out.println(sortByMax(alWinfo).toString());
		sInfos.sortByMax();
	}

	/**
	 * 
	 * @param limit
	 *            of the first elements of the sorted list
	 * @return the summary String
	 */
	public synchronized String getResult(int limit) {
		return sInfos.getResult(limit);
	}

	/**
	 * Write the summary to output file
	 * 
	 * @param outFpath_str
	 * @param limit
	 * @throws IOException
	 */
	public synchronized void writeResult(final String outFpath_str, int limit) throws IOException {
		Path outFpath = Paths.get(outFpath_str);
		File file = new File(outFpath.toString());
		if (overwriteOLD_outF) {
			file.delete();
		}
		System.out.println("Writing summary to " + outFpath);
		String outStr = ("scanned path -> " + this.scannedPath + "\n" + getResult(limit) + "\n\nfiles excluded due to exception: "
				+ getExcScannedFList());
		Files.write(outFpath, outStr.getBytes());// AutoCreated if not exsist
	}

	/**
	 * Scan the input file and update the input SummaryInfos 
	 * @param file
	 * @param hmap
	 * @param excludedWords
	 */
	private void scanFile(final File file, final SummaryInfos sInfos, final HashSet<String> excludedWords) {
		try {
			FileScan fscan = new FileScan();
			fscan.scanFile(file, sInfos, excludedWords);
		} catch (Exception e) {
			scannedFwithExc.add(file.getName());
			System.out.println(file.getName() + e.getMessage());
		}
	}

	/**
	 * Get the String list of the files excluded by exception in the last scan
	 * 
	 * @return
	 */
	private String getExcScannedFList() {
		StringBuilder sb = new StringBuilder();
		for (String fname : this.scannedFwithExc) {
			sb.append("\n" + fname);
		}
		return sb.toString();
	}

} // END CLASS
