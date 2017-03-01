package cards;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;



public class Game {

	private Deck deck;
	private List<Hand> players;
//	private Hand hand;
	private Comparator<Card> comparator;
	
	
	public Game(){
		
		deck = new Deck();
		players = new ArrayList<Hand>();
		comparator = new RankComparator();
		//another implementation of comparator
		comparator = new RankAndSuitComparator();
	}

	public void addPlayer(Hand hand){
		
		players.add(hand);
	}
	
	public Hand play(int numOfCards){    //// minst antal kort som delas ut 
		
		deck.shuffleCards();


/*		if(deck.getNoOfCards() < numOfCards * players.size()){ 			////// kollar om aktuellt antal kort ggr antal spelare är större än totala antal kort i kortleken
			
			return null;         									 	////// det innebär att det inte finns tillräckligt med kort för att dela till alla spelare
								  										///// throw new Exception("There is no enough cards for the game");
			
		}*/
		
		try {
			for(int i = 0 ; i < numOfCards; i++){
				
				for(Hand player: players){

					player.addCard(deck.dealCards());
					
				}
				
			}
		} catch (NoSuchCardException e) {
			e.printStackTrace();
			return null;
		}
		
		// räknar ut summan av alla kort och sedan jämför med andra summor
		// den spelaren som har störst summa vinner spelet
		
		int maxSum = 0;
		Hand maxPlayer = null;
		
		for(Hand player: players){
			
			int sum = getHandSum(player);  /////går till getHandSum och summerar alla kort
			
			if(maxSum < sum){
				
				maxSum = sum;
				maxPlayer = player;
				
			} else if(maxSum == sum) {
				
				// upredi zadnju podeljenu kartu pozivom metode compare

				try {
					Card maxCard = maxPlayer.getCard(maxPlayer.getNoOfCards()-1);
					Card card = player.getCard(player.getNoOfCards()-1);
					if(comparator.compare(card,maxCard) > 0){
						
						maxSum = sum;
						maxPlayer = player;
					
					}
				} catch (NoSuchCardException e) {
					e.printStackTrace();
					return null;
				}
				
								
				
			} 
			
		}
		
		return maxPlayer;
		
	}
	
	
	private int getHandSum(Hand player){  
		
		int sum = 0;
		
		for(int i = 0; i < player.getNoOfCards(); i++) {
			
			sum += player.getCard(i).getRank().getNumber();
			
		}
		
		return sum;
	}
	
	
	
	
}
