package cards;

public class NoSuchCardException extends RuntimeException {

	private int position;
	
	public NoSuchCardException(String message, int position){
		super(message);
		this.position = position;
	}
	
	public String getMessage(){
		String message = super.getMessage();
		message+= " Requested position was "+position;
		return message;
	}
}
