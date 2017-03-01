/**
 * The class Card creates a card by Rank and Suit, checks if it is turned up or
 * not.
 * 
 * 
 * @author Tanvir & Bratislav
 * 
 * 
 */

package cards;


public class Card 
{
	
	private Suit suit;
	private Rank rank;
	private boolean turnedUp;
	
        
      /**
	 * The constructor Card creates the card after rank, suit and turnedUp.
	 * 
         * @param rank Rank of the card.
         * @param suit Suit of the card.
	 * 
	 * 
	 */
	
	public Card(Rank rank, Suit suit)
        {
		turnedUp = true;
		this.suit = suit;
		this.rank = rank;
	}
        
       /**
	 * Gets the rank of the card.
	 * 
	 * 
	 * 
	 */

	public Rank getRank()
        {
		
		return rank;
	}
        
       /**
	 * Gets the suit of the card.
	 * 
	 * 
	 * 
	 */

	
	public Suit getSuit()
        {
		
		return suit;
	}
        
       /**
	 * Checks if the card is turned up.
	 * 
	 * 
	 * 
	 */  
        

	public boolean isTurnedUp() {
		return turnedUp;
	}

       /**
	 * Sets the card so it is turned up.
	 * 
	 * 
	 * @param turnedUp The value to be set if the card is to be turned up.
	 */   
        
	public void setTurnedUp(boolean turnedUp) 
        {
		this.turnedUp = turnedUp;
	}

       /**
	 * Checks if a card is equal to another card.
	 * 
	 * 
	 * @param card The card to be compared.
	 */  
	
	public boolean equals(Card card)
        {
		
		if(super.equals(card)){
			return true;
		}

		return getRank() == card.getRank() && getSuit() == card.getSuit();
		
	}
	
	
	
	

}