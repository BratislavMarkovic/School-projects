/**
 * The class Player represents the balance, chip and the table for each player.
 * It inherits the class Hand.
 * 
 * 
 * @author Tanvir & Bratislav
 * 
 * 
 */


package blackjack;

import cards.Hand;

public class Player extends Hand {

	private int balance;
	private int chip;
	private Table table;
        
        
       /**
	 * The constructor Player creates a table and name for the player.
	 * 
	 * 
	 * @param table Creates a table for the player.
	 * @param name Creates a name for the player.
	 * 
	 */
        
	
	public Player(String name, Table table) {
		super(name);
		this.table = table;
	}

       /**
	 * Deposit of the balance if any player or the dealer has won.
	 * 
	 * 
	 * @param amount Amount of how much have been betted.
	 * 
	 */
	
	public void deposit(int amount){
	
		balance+=amount;
		
	}
        
       /**
	 * Checks if a player is able to bet.
	 * 
	 * 
	 */
	
	
	public boolean isAbleToBet(){
		return table.getCurrentGame().isAbleToRegister(this) && balance >= 100;
	}
        
        
       /**
	 * Allows a player to bet an amount.
	 * 
	 * @param amount The amount to be betted.
	 */
        
	
	public void bet(int amount){
		
		
		
		// check if amount doesn't exceed the deposit
		
		// check if player is able to bet
		
		if(!isAbleToBet()){
			
			// throw an exception "not able to bet"
			
			throw new RuntimeException("Player "+getName()+" is not able to bet at the moment.");
			
		}
		
		
		if(chip == 0){
			
			table.getCurrentGame().registerPlayer(this);
			
		}
		
		balance-=amount;
		chip+=amount;
		
	}
	
       /**
	 * Gets the chip for a player.
	 * 
	 * 
	 */    
        
        
	public int getChip(){
		return chip;
	}
	
       /**
	 * Sets the chip for a player.
	 * 
	 * @param amount The amount of chips to be set.
	 */      
        
	public void setChip(int amount) {
		
		this.chip = amount;
		
	}
        
       /**
	 * Checks if player is able to stand.
	 * 
	 * 
	 */    
	
	public boolean isAbleToStand(){
		return table.getCurrentGame().isAbleToStand(this);
	}
	
       /**
	 * Allows the player to stand.
	 *
	 * 
	 */     
        
	public void stand(){
		
		table.getCurrentGame().stand(this);
		
	}
        
       /**
	 * Checks if player is able to hit.
	 *
	 * 
	 */       
        
	
	public boolean isAbleToHit(){
		return table.getCurrentGame().isAbleToHit(this);
	}
	
        
       /**
	 * Allows the player to hit.
	 *
	 * 
	 */            
        
	public void hit(){
		table.getCurrentGame().hit(this);
	}
	
       /**
	 * Checks if player is able to push.
	 *
	 * 
	 */       
        
        
	public boolean isAbleToPush(){
		return table.getCurrentGame().isAbleToPush(this);
	}
        
        
       /**
	 * Allows the player to push.
	 *
	 * 
	 */     
	
	public void push(){
		table.getCurrentGame().push(this);
	}
        
       /**
	 * Checks if the player is registered.
	 *
	 * 
	 */        
        
	
	public boolean isRegistered()
        {
		return table.getCurrentGame().isPlayerRegistered(this);
	}
        
        
       /**
	 * Gets the result of the player.
	 *
	 * 
	 */         
        
	
	public PlayResult getResult(){
		return table.getCurrentGame().getPlayerResult(this);
	}
	
       /**
	 * Checks if player is removable.
	 *
	 * 
	 */    
        
        
	public boolean isRemovable(){
		
		return table.getCurrentGame().isRemoveable(this);
		
	}
        
       /**
	 * Allows player to be removed.
	 *
	 * 
	 */     
        
	
	public void remove(){
		
		balance += chip;
		
	}
	
       /**
	 * Shows the information of a player.
	 *
	 * 
	 */    
        
	
	public String toString(){
		return getName()+", bal = "+balance + ", chip = " +chip;
	}
	
}
