/**
 * The class Hand is the hand of the player, gets the list of cards, amount of
 * a list of cards (sum).
 * 
 * 
 * @author Tanvir & Bratislav
 * 
 * 
 */

package cards;

import java.util.ArrayList;
import java.util.List;


public class Hand {
	
	
		private String name;
		private List<Card> gotCards;     
	
              /**
                * The constructor Hand creates the name of the player and a empty
                * space for cards.
                * 
                * @param name Name of the player.
                * 
                * 
                */
	
                
		public Hand(String name)
                {		
		
			this.name = name;
			gotCards = new ArrayList<Card>();
		
		} 
                
              /**
                * Gets the number of cards on a players hand. 
                *
                * 
                * 
                */
                
		public int getNoOfCards(){
		
			return gotCards.size();      /////retunerar aktuellt antal kort i handen
		
		}            
                
              /**
                * Adds a card to the players hand.
                *
                * @param c The card to be added to the hand.
                * 
                */
	
		public void addCard(Card c){
			
			if(c == null){
				
				throw new IllegalArgumentException("Card cannot be null");
				
			}
		
			gotCards.add(c);										//////lägger till ett kort i handen
		
		}
                
	      /**
                * Gets the card on a certain position in hand.
                *
                * @param p The position of the card to be get.
                * 
                */
                
		public Card getCard(int p){
		
			if(p < 0 || p >= gotCards.size()){
			
				throw new NoSuchCardException("Card with index " + p + " does not exist!",p);
			
			}
		
			return gotCards.get(p);		 /////retunerar referens till kortet på angiven position
		
		} 
	
              /**
                * Removes the card on a certain position in hand.
                *
                * @param i The position of the card to be removed.
                * 
                */ 
                
		public Card removeCard(int i){
		
			if(i < 0 || i >= gotCards.size()){
				
				throw new NoSuchCardException("Card with index " + i + " does not exist!",i);
			}
		
			return gotCards.remove(i);
		}
	
	      /**
                * Clears the hand from cards.
                * 
                * 
                * 
                */                
                
		public void clear()
                {
			gotCards.clear();
		}
		      
              /**
                * Gets the name of the player.
                * 
                * 
                * 
                */    
	
		public String getName() 
                {
			return name;
		}

              /**
                * Sets the name of the player.
                * 
                */    
                
		public void setName(String name)
                {
			this.name = name;
		}

                
              /**
                * Prints out information about the players number and name.
                * 
                */ 
                
		public String toString()
                {
		
			return "Player nr. " + name;
			
		}

}
