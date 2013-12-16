package exception;

import android.util.Log;

public class ExceptionHandler extends Exception {

	private static final long serialVersionUID = 1L;
	private static String LogTag = new String("ExceptionHandler");
	private static String ERROR_MESSAGE;
	public static String networkError = new String("Network Failure!");
	private int ERROR_CODE;
	private String MESSAGE;
	
	// ExceptionHandler Constructor records the ERROR_CODE and ERROR_MESSAGE 
	public ExceptionHandler(int err, String message) {
		MESSAGE = message;
		ERROR_CODE = err;
	}
	
	// Method returns details for Exception thrown in string variable
	public String exceptionHandle() {
		ERROR_MESSAGE = "Error #" + ERROR_CODE + ": " + MESSAGE;
		Log.e(LogTag, ERROR_MESSAGE);
		return MESSAGE;
	}
}
