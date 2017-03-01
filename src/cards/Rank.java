package cards;

/**
 * The enum Rank is an enumeration that defines the ranks of the cards.
 * Those are Ace, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack,
 * Queen, King.
 * 
 * @author Tanvir & Bratislav
 * 
 * 
 */

public enum Rank {
	
	Ace(1,11,0), Two(2,2,12), Three(3,3,11), Four(4,4,10), Five(5,5,9), Six(6,6,8), Seven(7,7,7), Eight(8,8,6), Nine(9,9,5), Ten(10,10,4), Jack(12,10,3), Queen(13,10,2), King(14,10,1);
	
	private int number;
	private int value;
	private int index;
	
       /**
	 * The constructor Rank creates the number, value and index for the rank.
	 * 
	 * 
	 * @param number Number for the rank.
         * @param value Value for the rank.
   	 * @param index Index for the rank.
	 * 
	 * 
	 */
        
	private Rank(int number, int value, int index)
        {
		this.number = number;
		this.value = value;
		this.index = index;
	}
	
       /**
	 * Gets the number of the rank.
	 * 
	 * 
	 */
        
	public int getNumber()
        {
		return this.number;
	}
        
       /**
	 * Gets the value of the rank.
	 * 
	 * 
	 */
        
	public int getValue()
        {
		return value;
	}
        
       /**
	 * Gets the index of the rank.
	 * 
	 * 
	 */   
        
	public int getIndex()
        {
		return index;
	}
}
