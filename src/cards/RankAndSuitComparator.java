package cards;

import java.util.Comparator;

public class RankAndSuitComparator implements Comparator<Card> {

	@Override
	public int compare(Card first, Card second) {
		
		return first.getRank().getNumber() + first.getSuit().getNumber() - 
				
				(second.getRank().getNumber() + second.getSuit().getNumber());
		
		
	}

}
