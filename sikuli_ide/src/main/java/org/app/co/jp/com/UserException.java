package org.app.co.jp.com;

public class UserException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private String message = ""; 
	
	public UserException(String strMessage) {
		message = strMessage;
	}
	
	public String getMessage() {
		return message;
	}
}
