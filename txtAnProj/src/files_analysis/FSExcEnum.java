/**
 * 
 */
package files_analysis;

/**
 * @author Michele.LONGOBARDO
 *
 */
public enum FSExcEnum {
	EXC_FILE_EXTENSION(0, " discarded extension"),
	NOT_A_FILE(1, " not a file");

	
	private final int id;
	private final String msg;

	FSExcEnum(int id, String msg) {
		this.id = id;
		this.msg = msg;
	}

	public int getId() {
		return this.id;
	}

	public String getMsg() {
		return this.msg;
	}
}
