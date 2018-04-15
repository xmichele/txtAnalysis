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
	private  SummaryInfos sInfos = new SummaryInfos();
	private String scannedPath = "";
	private HashSet<String> scannedFwithExc = new HashSet<>();
	private static final String default_in_path = "C:\\Users\\Michele.LONGOBARDO\\Desktop";
	private static final String default_out_path = ".\\outSumm.txt";
	
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
				in_path = args[0];// TODO param (extension needed
				out_path = args[1];
			}
		}
		final HashSet<String> excludedWords = new HashSet<>(); // TODO params (i.e. by cmd  line args)
		TextFilesAnalysis txtAn = new TextFilesAnalysis();
		txtAn.executeScan(in_path, excludedWords);
		String report = txtAn.getResult(10);
		System.out.println(report);
		try {
			txtAn.writeResult(out_path, 10);
		} catch (IOException e) {
			e.printStackTrace();
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
			//the multi-depthLevel scanning feature could be provided
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
	 * @return the summary String
	 */
	public synchronized String getResult(int limit) {
		return sInfos.getResult(limit);
	}

	/**
	 * Write the summary to output file
	 * @param outFpath_str
	 * @param limit
	 * @throws IOException
	 */
	public synchronized void writeResult(final String outFpath_str, int limit) throws IOException {
		Path outFpath = Paths.get(outFpath_str);
		File file = new File(outFpath.toString());
		file.delete();
		//file.createNewFile();TODO new File .delete (if exsists...)
		System.out.println("Writing summary to " + outFpath);
		String outStr = ("scanned path -> " + this.scannedPath + "\n" + getResult(limit) + "\n\nfiles excluded due to exception: " + getExcScannedFList());
		Files.write(outFpath, outStr.getBytes());//AutoCreation if not exsist

	}

	/**
	 * TODO
	 * 
	 * @param f
	 * @param hmap
	 * @param excludedWords
	 */
	private void scanFile(final File f, final SummaryInfos sInfos, final HashSet<String> excludedWords) {
		try {
			FileScan fscan = new FileScan();
			fscan.scanFile(f, sInfos, excludedWords);
		} catch (Exception e) {
			scannedFwithExc.add(f.getName());
			System.out.println(f.getName() + e.getMessage());
		}
	}

	/**
	 * Get the String list of the files excluded by exception in the last scan
	 * @return
	 */
	private String getExcScannedFList() {
		StringBuilder sb = new StringBuilder();
		for (String fname : this.scannedFwithExc) {
			sb.append("\n" + fname);
		}
		return sb.toString();
	}

} //END CLASS
