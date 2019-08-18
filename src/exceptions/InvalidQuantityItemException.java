package exceptions;

public class InvalidQuantityItemException extends Exception{
	
	public InvalidQuantityItemException() {
		super("Invalid Quantity Items");
	}
	
	public InvalidQuantityItemException(String message) {
		super(message);
	}
}
