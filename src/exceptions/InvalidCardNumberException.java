package exceptions;

public class InvalidCardNumberException extends Exception{

	public InvalidCardNumberException() {
		super("Invalid Card Number");
	}
	
	public InvalidCardNumberException(String message) {
		super(message);
	}
	
}
