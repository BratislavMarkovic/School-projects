package cards;

	


	public class Main {

		public static void main(String[] args) {
		
			Game game = new Game();
		
			Hand player1 = new Hand("1");
		
			game.addPlayer(player1);

		
			Hand player2 = new Hand("2");
		
			game.addPlayer(player2);
			
			
			Hand player3 = new Hand("3");
			
			game.addPlayer(player3);
		
		
		
			Hand theWinner = game.play(3);
		
			System.out.println("The winner is " + theWinner);
			

		}
	
	}
