/**
 * The class Game defines the rules, game states and result of the game.
 * 
 * 
 * @author Tanvir & Bratislav
 * 
 * 
 */

package blackjack;

import java.util.Collection;
import java.util.HashSet;

import cards.Card;
import cards.Deck;
import cards.Hand;
import cards.Rank;

public class Game {
	
	private Table table;
	private Player activePlayer;
	private GameState state;
	private Collection<Player> players;
	private Collection<Player> pushedPlayers;
	private Deck deck;

	
	/**
	 * The constructor Game creates an instance objects of classes table, active players, deck, awaitingBet states.
	 * 
	 * 
	 * @param table Creates a table, the class that hold the references on players and currentGame and balance.
	 * 
	 * 
	 */
	
	public Game(Table table){
		this.table = table;
		players = new HashSet<Player>();
		pushedPlayers = new HashSet<Player>();
		state = GameState.awaitingBets;
		activePlayer = null;
		deck = new Deck();
		deck.shuffleCards();
	}
	
	/**
	 * Hold references on players that are registered for the game.
	 * 
	 * 
	 * @param player Register a player.
	 * 
	 * 
	 */
	
	
	public void registerPlayer(Player player){
		players.add(player);
	}
	
	
	/**
	 * Hold references on players that are unregistered for the game.
	 * 
	 * 
	 * @param player Unregister a player.
	 * 
	 * 
	 */
	
	public void unregisterPlayer(Player player){
		players.remove(player);
	}
	
	/**
	 * Checks if the player is registered for the game
	 * 
	 * 
	 * @param player Represents the specific player object.
	 * 
	 * 
	 */
	
	public boolean isPlayerRegistered(Player player) {
		
		return players.contains(player) || pushedPlayers.contains(player);
	
	}
	
	/**
	 * Checks if it is able to deal cards.
	 * 
	 * 
	 * 
	 */
	
	public boolean isAbleToDealCards(){
		// TODO: implement this method ;)
		
		return !players.isEmpty() && state == GameState.awaitingBets;

		
	}
	
	
	
	/**
	 * Dealing cards to players by checking the state of game.
	 * 
	 * 
	 * 
	 */
	
	public void dealCards(){
		
		if(!isAbleToDealCards()){
			
			throw new RuntimeException("Wrong state of the game or no registred players");
		}
		
		for(Hand player: table.getPlayers()){
			if(player == null){
				continue;
			}
			for(int i = 0 ; i < 2; i++){

				if(players.contains(player)) {

					player.addCard(deck.dealCards());
					
				}
			
			}
			
		}
		table.getDealer().addCard(deck.dealCards());
		Card c = deck.dealCards();
		c.setTurnedUp(false);
		table.getDealer().addCard(c);
		
		setNextActivePlayer();
		
		state = GameState.playersTurn;
	}
	
	
	/**
	 * Checks if it is able to register a player.
	 * 
	 * @param player The specific player object to be checked if it is able to register.
	 * 
	 */
	
	
	public boolean isAbleToRegister(Player player){
		
		return state == GameState.awaitingBets;
	}
	
	/**
	 * Checks if it an specific player is able to hit(get a new card)
	 * 
	 * 
	 * @param player The specific player object to be checked if it is able to hit.
	 */
	
	public boolean isAbleToHit(Player player){
		
		return activePlayer == player;
	}
	
	/**
	 * Allows any player to hit a new card
	 * 
	 * 
	 * @param player The specific player object to hit.
	 */
	
	public void hit(Player player){
		
		if(!isAbleToHit(player)){
			
			throw new RuntimeException("Player " + player.getName() + " is not able to hit!");
		}
		
		player.addCard(deck.dealCards());
		
		if(getSum(player) >= 21){
			
			proceedGame();
			
		}
	}
	
	
	/**
	 * Checks if it an specific player is registered to printing result.
         * The player can get any type of result: PUSH, BLACKJACK, LOST, WIN.
	 * 
	 * 
	 * @param player The specific player object to be checked if corresponding 
         * result should be printed.
	 */
	
	public PlayResult getPlayerResult(Player player){
		
		if(!isPlayerRegistered(player)){
			
			throw new RuntimeException("Player didn't play this game");
		
		}
		
		if(pushedPlayers.contains(player)) {
			
			return PlayResult.PUSH;
		
		}
		
		if(getSum(player) > 21) {
			
			return PlayResult.LOST;
			
		} 
		
		if(state != GameState.gameOver){
			
			return PlayResult.UNKNOWN;
		
		} else {

			if(getSum(table.getDealer()) > 21) {

				if(isBlackJack(player)){
					
					return PlayResult.BLACK_JACK;
				
				} else {
					
					return PlayResult.WIN;
				
				}
				
			} else if(getSum(player) == getSum(table.getDealer())) {
				
				if(player.getNoOfCards() > table.getDealer().getNoOfCards()) {
					
					return PlayResult.LOST;	
					
				} else if (player.getNoOfCards() < table.getDealer().getNoOfCards()) {
					
					if(isBlackJack(player)){
						
						return PlayResult.BLACK_JACK;
					
					} else {
						
						return PlayResult.WIN;
					
					}
					
				} else {
					
					return PlayResult.PUSH;
					
				}
				
			} else if(getSum(player) > getSum(table.getDealer())) {
				
				if(isBlackJack(player)){
					
					return PlayResult.BLACK_JACK;
				
				} else {
					
					return PlayResult.WIN;
				
				}
				
			} else {
				
				return PlayResult.LOST;
				
			}
			
		}
	}
	
	/**
	 * Handles the player that has got the Black Jack with a reward.
	 * 
	 * 
	 * @param player The specific player object that got a BlackJack.
	 */
	
	private void handleBlackJack(Player player){
		// house lost
		
		player.deposit(player.getChip()*2);
		table.withdraw(player.getChip());
		
		player.deposit(player.getChip()/2);
		table.withdraw(player.getChip()/2);
		
		player.setChip(0);
	}
	
        
	/**
	 * Handles the player that has won (got near 21) with a reward.
	 * 
	 * 
	 * @param player The specific player object that has won.
	 */
        
        
	private void handleWon(Player player){
		
		// house lost
		
		player.deposit(player.getChip() * 2);
		table.withdraw(player.getChip());
		
		player.setChip(0);
		
	}
        
        
       /**
	 * Handles the player that has lost.
	 * 
	 * 
	 * @param player The specific player object that has lost.
	 */
	
	private void handleLost(Player player) {
		
		// // lost, take money
		table.deposit(player.getChip());
		
		player.setChip(0);
		
		
	}
        
        
        /**
	 * Handles the player that has been pushed.
	 * 
	 * 
	 * @param player The specific player object that has been pushed.
	 */

	private void handleForcedPush(Player player){
		
		player.deposit(player.getChip());
		player.setChip(0);

	}
	
      /**
	 * Handles the player that is to be pushed on the game.
	 * 
	 * 
	 * @param player The specific player object that is to be pushed.
	 */
	
	private void handlePush(Player player){
		
		player.deposit(player.getChip());
		
		player.setChip(0);
		players.remove(player);
		pushedPlayers.add(player);

	}
        
        
       /**
	 * Proceeds the game by continuing after the cards have been dealed to
         * the players. The dealer will then turn up the second card and the
         * game result will be controlled.
	 * 
	 */
        
        
        
	private void proceedGame(){
		
		setNextActivePlayer();
		
		if(activePlayer == null) {
			
			// turn up the second card of dealer
			
			table.getDealer().getCard(1).setTurnedUp(true);
			
			
			boolean allLost = true;
			
			for(Player player : players){
				
				if(getSum(player) <= 21){
					allLost = false;
					break;
				}
				
			}
			
			if(!allLost) {
				
				while(getSum(table.getDealer()) < 17) {
					
					table.getDealer().addCard(deck.dealCards());
					
				}
			
			}
			
			// set end of the game
			
			state = GameState.gameOver;
			
			
			
			// calculate the game
			
			for(Player player : players) {
				
				switch(getPlayerResult(player)){
					
					case BLACK_JACK:
						handleBlackJack(player);
						break;
					case WIN:
						handleWon(player);
						break;
					case LOST:
						handleLost(player);
						break;
					case PUSH:
						handleForcedPush(player);
						break;
					default:
						throw new RuntimeException("Unexpected game outcome:  " + getPlayerResult(player).name());	
				
				}
				
			}
			
		}

	}
	
        /**
	 * Checks if a player is removable from the game by checking the current
         * game state.
         * 
         * @param player The player object to be checked if it is removable.
	 * 
	 */
              
	public boolean isRemoveable(Player player){
		
		if(state == GameState.awaitingBets || state == GameState.gameOver){
			return true;
		}
		
		if(!isPlayerRegistered(player) || getPlayerResult(player) == PlayResult.PUSH) {
			
			return true;
			
		}
		
		return false;
		
	}
	
        
       /**
         * Counts the sum of card and the number of cards, checks if it is 
         * BlackJack.
         * 
         * @param hand The hand to be controlled if it is an BlackJack.
	 * 
	 */
        
	private boolean isBlackJack(Hand hand){
		return hand.getNoOfCards() == 2 && getSum(hand) == 21;
	}
	
        
       /**
         * Checks if a player is able to stand. 
         * 
         * @param player The player object to be checked if it is able to hit.
	 * 
	 */
        
        
	public boolean isAbleToStand(Player player){
		
			return activePlayer == player;
		
	}
	
        
       /** 
         * Allows a player to stand by not taking another cards.
         * 
         * @param player The player object that will hit on the game.
	 * 
	 */    
        
        
	public void stand(Player player){
		
		if(!isAbleToStand(player)) {
			
			throw new RuntimeException("Player " + player.getName() + " is not able to stand!");
			
		}
		
		proceedGame();
				
	}
        
       /** 
         * Checks if a player is able to push on the game.
         * 
         * @param player The player to be checked if it is able to push.
	 * 
	 */     
        
	
	public boolean isAbleToPush(Player player){
		return player == activePlayer && player.getNoOfCards() == 2;
	}
        
        
       /** 
         * Allows a player to push.
         * 
         * @param player The player that will push.
	 * 
	 */         
        
	
	public void push(Player player){
		
		if(!isAbleToPush(player)){
			
			throw new RuntimeException("Player " + player.getName() + " is not able to push!");
			
		}
		
		handlePush(player);
		
		proceedGame();
		
	}
        
       /** 
         * Gets the sum of a hand.
         * 
         * @param hand The hand to get the sum for (cards).
	 * 
	 */  	
	
	
	private int getSum(Hand hand){
		
		int sum = 0;
		boolean firstAs = false;
		
		for(int i = 0; i < hand.getNoOfCards(); i++){
			
			if(hand.getCard(i).getRank() == Rank.Ace){
				
				if(firstAs){
					
					sum += 1;
					continue;
					
				} else {
					
					firstAs = true;
					
				}
			}
				
			sum += hand.getCard(i).getRank().getValue();
		}
		
		return sum;
	}

       /** 
         * Sets the next player as active on the game.
         * 
	 * 
	 */      
        
        
	private void setNextActivePlayer(){

		boolean nextFound = false;
		
		for(Player player: table.getPlayers()){
			if(player == null){
				continue;
			}
			if(players.contains(player) && getSum(player) != 21 && (activePlayer == null || table.getPlayers().indexOf(player) > table.getPlayers().indexOf(activePlayer))){
				
				activePlayer = player;
				nextFound = true;
				break;
				
			}
	
		}
		
		if(!nextFound){
			
			activePlayer = null;
		}
	}
	
       /** 
         * Checks if the game is over.
         * 
	 * 
	 */     
        
        
	public boolean isOver(){
		return state == GameState.gameOver;
	}
	
	
}


