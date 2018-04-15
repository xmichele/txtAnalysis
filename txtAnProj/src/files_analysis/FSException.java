/**
 * 
 */
package files_analysis;

/**
 * @author Michele.LONGOBARDO
 *
 */
public class FSException extends Exception {
	private static final long serialVersionUID = 1L;//default sVUID
	
	private int errorCode;
	private String errorMsg;

	public FSException(FSExcEnum code) {
		this.errorMsg = code.getMsg();
		this.errorCode = code.getId();
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	@Override
	public String getMessage() {
		return this.getErrorMsg();
	}
	
}
