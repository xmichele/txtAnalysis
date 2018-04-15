/**
 * 
 */
package files_analysis;

/**
 * @author Michele.LONGOBARDO
 *
 */
public class WordInfo implements Comparable<WordInfo>{

	private String word;// considered word
	private int count; // number of occurrences of this word

	/**
	 * Constructor
	 * Initial value 1
	 * @param word
	 */
	public WordInfo(String word) {
		this.word = word;
		this.count = 1;
	}

	/**
	 * Constructor
	 * 
	 * @param word
	 * @param count
	 */
	public WordInfo(String word, int count) {
		this.word = word;
		this.count = count;
	}

	/**
	 * @return the count
	 */
	int getCount() {
		return count;
	}

	/**
	 * Increment the number of occurrences of the string word
	 */
	int incrWCount() {
		return (++this.count);
	}

	/**
	 * @return the word
	 */
	String getWord() {
		return word;
	}

	@Override
	public String toString() {
		return (this.word + " -> " + this.count);
	}

	@Override
	public int compareTo(WordInfo o) {
		return this.getCount() - o.getCount();
	}

}
