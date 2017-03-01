/**
 * The class Deck creates the card for the game (52 cards).
 * 
 * 
 * @author Tanvir & Bratislav
 * 
 * 
 */


package cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;



public class Deck {

		
	private ArrayList<Card> theCards;   
	
	
       /**
	 * The constructor Deck creates the 52 cards for the game.
	 * 
	 * 
	 * 
	 */
        
        
	public Deck()
        {
		
		theCards = new ArrayList<Card>();
		fill();
		
	}
	
       /**
	 * Gets the number of cards of Deck.
	 * 
	 * 
	 * 
	 */  
        
	
	public int getNoOfCards(){
		
		return theCards.size();
		
	}		

       /**
	 * Deals the cards to dealer and player.
	 * 
	 * 
	 * 
	 */    
	
	public Card dealCards(){
		
		if(theCards.isEmpty()){ 					/////// kollar om theCards is empty, om så retunerar null.
			
			throw new NoSuchCardException("No more cards to deal!", -1);
		}
		
		return theCards.remove(theCards.size()-1);  /////// tar bort (size()-1) elementet och retunerar dess värde och förflyttar referensen till nästsista element i listan/arrayen
													/////// size() talar om antal element i listan/arrayen.
	}
	
       /**
	 * Shuffles the cards (mixing the cards).
	 * 
	 * 
	 * 
	 */     
        
	
	public void shuffleCards()
        {
		
		Collections.shuffle(theCards);
		
	}
	
       /**
	 * Fills the constructor with the 52 cards based on enumerations Suit
         * and Rank.
	 * 
	 * 
	 * 
	 */  
	
	public void fill(){
		
		theCards.clear();
		
		for(Suit suit : Suit.values()){
			
			for(Rank rank : Rank.values()){
				
				Card c = new Card(rank, suit);
				
				theCards.add(c);
				
				
			}
		}		
	}

}