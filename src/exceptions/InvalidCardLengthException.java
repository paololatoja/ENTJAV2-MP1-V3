package exceptions;

public class InvalidCardLengthException extends Exception{
	
	public InvalidCardLengthException() {
		super("Invalid Card Length");
	}
	
	public InvalidCardLengthException(String message) {
		super(message);
	}
	
}
