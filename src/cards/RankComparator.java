package cards;

import java.util.Comparator;

public class RankComparator implements Comparator<Card> {

	@Override
	public int compare(Card first, Card second) {
		
		// method getNumber returns the ordinal number of rank. 
				if(first.getRank().getNumber() > second.getRank().getNumber()) {
					
					return 1;
					
				} else if(first.getRank().getNumber() < second.getRank().getNumber()) {
					
					return -1;
				
				} else {
					
					return first.getSuit().getNumber() > second.getSuit().getNumber() ? 1 : -1 ; 
					
				}
		
	
	}

}
