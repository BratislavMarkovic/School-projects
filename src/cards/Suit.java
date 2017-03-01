package cards;

/**
 * The enum Suit is an enumeration that defines the suits of the cards.
 * Those are Spades, Hearts, Diamonds and Clubs.
 * 
 * @author Tanvir & Bratislav
 * 
 * 
 */

public enum Suit 
{
	Spades(1), Hearts(2), Diamonds(3), Clubs(4);
	
	private int number;
	
        
	/**
	 * The constructor Suit creates the number for the suit.
	 * 
	 * 
	 * @param number Number for the suit.
	 * 
	 * 
	 */
        
	private Suit(int number)
        {
		this.number = number;
	}
        
       /**
	 * Gets the number of the suit.
	 * 
	 * 
	 */
	
        public int getNumber()
        {
		return this.number;
	}
}
