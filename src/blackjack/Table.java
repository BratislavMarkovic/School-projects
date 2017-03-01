/**
 * The class Table represents the table for the game by dealer, balance, list of
 * players and the current game.
 * 
 * 
 * @author Tanvir & Bratislav
 * 
 * 
 */

package blackjack;

import java.util.ArrayList;
import java.util.List;

import cards.Hand;

public class Table {

	private Hand dealer;
	
	private int balance;

	private List<Player> players;
	
	private Game currentGame;

        
      /**
        * The constructor Table creates a list of players, the dealer, balance
        * and the game.
        * 
        * 
        * 
        */   
        
	public Table(){
		
		players = new ArrayList<Player>(4);
		dealer = new Hand("Dealer");
		balance = 100000;
		newGame();
	}
	
        
      /**
        * Creates the game for the table. 
        * 
        * 
        */ 
        
	public void newGame() {
		
		currentGame = new Game(this);
		
		for(Player player : players){
			
			if(player!=null) {
				
				player.clear();
			
			}
			
		}
		
		dealer.clear();
	}
        
        
      /**
        * Deposit for the dealer, if the dealer has won.
        * 
        * @param amount The amount of how much a player has bet.
        */ 
	
	public void deposit(int amount) {
		balance += amount;
	}
        
      /**
        * Withdraw for the dealer, if the dealer has lost.
        * 
        * @param amount The amount of how much a player has bet.
        */ 
	
	public void withdraw(int amount) {
		balance -= amount;
	}
	
       /**
        * Gets the balance of a player and dealer.
        * 
        */    
        
        
	public int getBalance(){
		return balance;
	}
        
       /**
        * Gets the list of players.
        * 
        */ 
	
	public List<Player> getPlayers(){
		return players;
	}
        
       /**
        * Gets the dealer of the game.
        * 
        */  
       
	public Hand getDealer(){
		return dealer;
	}
	
      /**
        * Gets the current game.
        * 
        */ 
        
        
	public Game getCurrentGame(){
		return currentGame;
	}
	
	
}
