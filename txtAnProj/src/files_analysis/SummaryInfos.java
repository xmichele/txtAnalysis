/**
 * 
 */
package files_analysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author Michele.LONGOBARDO
 *
 */
public class SummaryInfos {
	private ArrayList<WordInfo> alWinfo;
	private long totalWdiscarded;
	private long totalWIncr;
	private int totalLines;

	/**
	 * Constructor
	 */
	public SummaryInfos() {
		alWinfo = new ArrayList<WordInfo>();
		resetCounters();
	}

	/**
	 * Reset counters used for summary output
	 */
	private void resetCounters() {
		totalWdiscarded = 0L;
		totalWIncr = 0L;
		totalLines = 0;
	}

	/**
	 * @return the total_discarded
	 */
	long getTotalWdiscarded() {
		return totalWdiscarded;
	}

	/**
	 * @return the cumuled_incr
	 */
	long getTotalWincr() {
		return totalWIncr;
	}

	/**
	 * @return the totalLines
	 */
	int getTotalLines() {
		return totalLines;
	}

	/**
	 * Increment the total discarded words counter
	 * @return the total_discarded
	 */
	long incrTotalWdiscarded() {
		return ++totalWdiscarded;
	}

	/**
	 * Increment the total increments words counter
	 * @return the cumuled_incr
	 */
	long incrTotalWincr() {
		return ++totalWIncr;
	}

	/**
	 * Increment the total lines counter
	 * @return the totalLines
	 */
	int incrTotalLines() {
		return ++totalLines;
	}

	/**
	 *
	 * @param kword
	 * @return the incremented counter of found Winfo element, othewise null
	 */
	Integer incrWInfo(String kword) {
		for (WordInfo winfo : alWinfo) {
			if (winfo.getWord().equals(kword)) {
				return winfo.incrWCount();
			}
		} // END FOR
		return null;
	}

	/**
	 * Add new word(info) to the internal array
	 * @param word
	 */
	void addNewWInfo(String word) {
		alWinfo.add(new WordInfo(word));
	}

	/**
	 * Retrieve the summary String
	 * 
	 * @param limit
	 * @return the summary string
	 */
	String getResult(int limit) {
		ArrayList<WordInfo> redAlWinfo = getWInfoArr(limit);
		StringBuilder sb = new StringBuilder();
		for (WordInfo winfo : redAlWinfo) {
			sb.append(winfo.toString() + '\n');
		}

		sb.append("\ntotal lines scanned " + this.getTotalLines());
		sb.append("\ntotal words increments " + this.getTotalWincr());
		sb.append("\ntotal words discarded " + this.getTotalWdiscarded());
		return sb.toString();
	}

	/**
	 * Return a fresh instance of the sorted array of wordInfo
	 * 
	 * @param limit
	 * @return
	 */
	ArrayList<WordInfo> getWInfoArr(int limit) {
		return new ArrayList<WordInfo>(alWinfo.subList(0, limit));
	}

	/**
	 * sort by words with more occurrences
	 */
	void sortByMax() {
		Collections.sort(alWinfo, new Comparator<WordInfo>() {
			@Override
			public int compare(final WordInfo wi, final WordInfo wi2) {
				return (wi2.compareTo(wi));
			}
		});
	}
	
}// END CLASS
