package blackjack.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import blackjack.PlayResult;
import blackjack.Player;
import blackjack.Table;
import cards.Card;
import cards.Hand;
import cards.Rank;
import cards.Suit;

public class MainWindow extends JPanel implements ActionListener {

	
	private static int CARD_DISTANCE = 5;
	private static int DEALER_INDEX = -1;
	private Table table;
	private Map<Rank,Map<Suit,BufferedImage>> images;
	private Map<Integer,Map<String,JButton>> buttons;
	private BufferedImage verticalUpsideDown ;
	private BufferedImage upHalfCard ;
	private BufferedImage horizontalUpsideDown;
	private Timer timer;
	private boolean showResult = false;
	
	public MainWindow(){
		
		table = new Table();
		images = new HashMap<Rank, Map<Suit,BufferedImage>>();
		buttons = new HashMap<Integer,Map<String,JButton>>();
		//timer.start();
		try {
			verticalUpsideDown = ImageIO.read(Table.class.getClassLoader().getResourceAsStream("resource/cards/b1fv.png")); // turned card image
			upHalfCard = ImageIO.read(Table.class.getClassLoader().getResourceAsStream("resource/cards/b1pt.png")); // turned card image
			horizontalUpsideDown = ImageIO.read(Table.class.getClassLoader().getResourceAsStream("resource/cards/b1fh.png")); // turned card image
		} catch (Exception e) {
			e.printStackTrace();
		}
		setLayout(null);   // layout manager måste tilldelas värde null för att vi ska kunna bestämma exakta positioner på JPanel children(buttons)
							//då kommer JPanel att kolla po bounds för att kunna detektera "children", alltså våra buttons
		
		addButtons();
		
		addPlayerToTable(0,null);
		addPlayerToTable(1,null);
		addPlayerToTable(2,null);
		addPlayerToTable(3,null);	
		//testDealCards();
		timer = new Timer(500, this);
		timer.start();
	}
	
	private void addButtons(){
		
		// add dealer buttons
		
		Map<String,JButton> dealerButtons = new HashMap<String,JButton>();  /////////instancierar dealerButtons, dvs DealCards och New Game
		buttons.put(DEALER_INDEX, dealerButtons);
		
		JButton dealCards = new JButton("Deal cards");
		dealerButtons.put("Deal cards", dealCards);
		add(dealCards);						////////////lägger till buttons i JPanel
		
		
		dealCards.addActionListener(new TableAction(table,DEALER_INDEX) {     /////////////////////////anonymous class, definition och instanciering sker samtidigt, registrera actionListener för respektive button
																			///////////////////////// namnlös klass, extends tableAction		
			@Override         	/////////////////////////////controller/////////////////////////////
			public void actionPerformed(ActionEvent e) {
				
				if(table.getCurrentGame().isAbleToDealCards()){
					
					table.getCurrentGame().dealCards();
					repaint();																			///////repaints MainWindow
					
				} else {
					
					throw new RuntimeException("Not able to deal cards at this point. Check logic.");
					
				}
			}
		});
		
		
		
		JButton newGame = new JButton("New game");
		dealerButtons.put("New game", newGame);
		add(newGame);
		newGame.addActionListener(new TableAction(table,DEALER_INDEX) {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(table.getCurrentGame().isOver()){
					
					table.newGame();
					 
					
				} else {
					
					throw new RuntimeException("Not able to start new game. Check logic.");
				}
			}
		});

		
		// add player buttons
		
		
		for(int i = 0 ; i < 4 ; i++){
			
			Map<String,JButton> playerButtons = new HashMap<String,JButton>();
			buttons.put(i, playerButtons);
			
			
			JButton betButton = new JButton("Bet");
			playerButtons.put("Bet", betButton);
			add(betButton);
			betButton.setVisible(false);
			betButton.addActionListener(new TableAction(table,i) {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					Player player = table.getPlayers().get(position);
					
					if(player.isAbleToBet()){
						
						player.bet(100);
						repaint();   
						
					} else {
						
						throw new RuntimeException("Player is not able to bet now. Check logic.");
						
					}
					
				}
			});

			
			JButton hitButton = new JButton("Hit");
			playerButtons.put("Hit", hitButton);
			add(hitButton);
			hitButton.setVisible(false);
			hitButton.addActionListener(new TableAction(table,i) {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					Player player = table.getPlayers().get(position);
					
					if(player.isAbleToHit()){
						
						player.hit();
						repaint();
						
					} else {
						
						throw new RuntimeException("Player is not able to hit. Check your logic.");
						
					}
					
				}
			});

			
			JButton standButton = new JButton("Stand");
			playerButtons.put("Stand", standButton);
			add(standButton);
			standButton.setVisible(false);
			standButton.addActionListener(new TableAction(table,i) {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					Player player = table.getPlayers().get(position);
					
					if(player.isAbleToStand()){
						
						player.stand();
						repaint();
						
					} else {
						
						throw new RuntimeException("Player is not able to stand. Check your logic.");
						
					}
					
				}
			});

			
			JButton pushButton = new JButton("Push");
			playerButtons.put("Push", pushButton);
			add(pushButton);
			pushButton.setVisible(false);
			pushButton.addActionListener(new TableAction(table,i) {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					Player player = table.getPlayers().get(position);
					
					if(player.isAbleToPush()){
						
						player.push();
						repaint();
						
					} else {
						
						throw new RuntimeException("Player is not able to push. Check your logic.");
						
					}
					
				}
			});

			
			JButton addPlayerButton = new JButton("Add player");
			playerButtons.put("Add player", addPlayerButton);
			add(addPlayerButton);
			addPlayerButton.setVisible(false);
			addPlayerButton.addActionListener(new TableAction(table,i) {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					String playerName = (String)JOptionPane.showInputDialog(getParent(), "Enter player name:");
					
					if(playerName.equals("")){
						
						JOptionPane.showMessageDialog(getParent(), "You haven't entered players name. Try again.");
						
					} else {
					
						Player player = new Player(playerName,table);
						table.getPlayers().remove(position);
						table.getPlayers().add(position, player);
						repaint();
						
					}
					
					
					
				}
			});
			
			JButton removePlayerButton = new JButton("Remove player");
			playerButtons.put("Remove player", removePlayerButton);
			add(removePlayerButton);
			removePlayerButton.setVisible(false);
			removePlayerButton.addActionListener(new TableAction(table,i) {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					Player player = table.getPlayers().get(position);
					
					if(player.isRemovable()){
						
						player.remove();
						table.getPlayers().remove(position);
						table.getPlayers().add(position, null);
						repaint();
						
					} else {
						
						throw new RuntimeException("Player is not removable. Check logic");
						
					}
					
				}
			});

			JButton addBalanceButton = new JButton("Add balance");
			playerButtons.put("Add balance", addBalanceButton);
			add(addBalanceButton);
			addBalanceButton.setVisible(false);
			addBalanceButton.addActionListener(new TableAction(table,i) {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					Player player = table.getPlayers().get(position);
					
					String amount = (String)JOptionPane.showInputDialog(getParent(), "Enter amount:");
					
					if(amount == null){

						JOptionPane.showMessageDialog(getParent(), "You haven't entered valid deposit. Try again.");
						
					} else {
						
						int value = 0;
						
						try {
							value = Integer.parseInt(amount);
						} catch (Exception e2) {
							
						}
						
						if(value == 0) {
							JOptionPane.showMessageDialog(getParent(), "You haven't entered valid deposit. Try again.");
						} else {
							
							player.deposit(value);
							repaint();
							
						}
						
					}
					
				}
			});
			
			
		}
		
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {

		if(table.getCurrentGame().isOver()){
			
			showResult = !showResult;
			repaint();
			
		} else {
			
			showResult = false;
			repaint();
		}
		
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);			
		
		// drawing table
		
		g.setColor(new Color(250,128,114));
		g.fillRect(getX(), getY(), getWidth(), getHeight());
		g.setColor(new Color(0,153,0));
		g.fillOval(getX()+50, getY()+50, getWidth()-100, getHeight()-400);

		
		// drawing dealer
		
		drawDealer(g);
		
		
		// drawing players
		int i = 0;

		for(Player player : table.getPlayers()){

			drawButtons(g,i,player);
			
			if(player!= null) {
				
				drawPlayer(g,i, player);
				
			} 
			
			i++;
		}
		
	}
	
	private BufferedImage getCardImage(Card card){
		// simple caching of buffered images using map of maps for combination of rank and suite
		
		if(!card.isTurnedUp()){
			return verticalUpsideDown;
		}
		
		Map<Suit,BufferedImage> suitImages = images.get(card.getRank());
		
		if(suitImages == null){
			// if there is no map , create it and add it for specified rank
			suitImages = new HashMap<Suit,BufferedImage>();
			images.put(card.getRank(), suitImages);
		}
		
		BufferedImage image = suitImages.get(card.getSuit());
		
		if(image == null){
			// if there is no image for suit, load it from file and put it in the map for next time
			try {
				image = ImageIO.read(Table.class.getClassLoader().getResourceAsStream("resource/cards/" + (((card.getRank().getIndex()) * 4)+card.getSuit().getNumber())+".png"));
			} catch (IOException ioe) {
				ioe.printStackTrace();
				throw new RuntimeException("OPS, missing card image",ioe);
			} catch(Exception e){
				throw new RuntimeException("OPS, fatal error for card file "+"resource/cards/" + (((card.getRank().getIndex()) * 4)+card.getSuit().getNumber())+".png",e);
			}
			suitImages.put(card.getSuit(), image);
		}
		
		return image;
	}
	
	private void drawDealer(Graphics g){
		Graphics2D g2d = (Graphics2D)g;
		
		// draw house balance
		
		Font f = new Font("Serif",Font.BOLD,30);
		Dimension textSize = getTextSize("House balance = "+table.getBalance(), g, f);
		g.setColor(Color.BLACK);
		g.setFont(f);
		g.drawString("House balance = "+table.getBalance(), getWidth()/2+50 , 30);
		
		
		// draw(enable) buttons
		
		JButton dealCards = buttons.get(DEALER_INDEX).get("Deal cards");
		
		if(table.getCurrentGame().isAbleToDealCards()){
			
			dealCards.setBounds(getWidth()/2-45, 10,90,30);
			dealCards.setVisible(true);
			
		} else {
			
			dealCards.setVisible(false);
			
		}
		
		JButton newGame = buttons.get(DEALER_INDEX).get("New game");
		
		if(table.getCurrentGame().isOver()){
			
			newGame.setBounds(getWidth()/2-45, 10,90,30);
			newGame.setVisible(true);
			
		} else {
			
			newGame.setVisible(false);
			
		}
		
		// draw deck
		
		g.drawImage(upHalfCard, getWidth()/2 - upHalfCard.getWidth()/2, 55, this);
		g.drawImage(upHalfCard, getWidth()/2 - upHalfCard.getWidth()/2, 55+upHalfCard.getHeight(), this);		
//		g.drawImage(upHalfCard, getWidth()/2 - upHalfCard.getWidth()/2, 55+upHalfCard.getHeight()*2, this);
		g.drawImage(horizontalUpsideDown, getWidth()/2 - upHalfCard.getWidth()/2, 55+upHalfCard.getHeight()*2, this);
		
		// draw cards
		
		if(table.getDealer().getNoOfCards() == 0){
			return;
		}
		
		
		AffineTransform original = g2d.getTransform();
		
		g2d.translate(getWidth()/2,170);
		
		drawCards(g2d, table.getDealer());
		
		g2d.setTransform(original);

	}
	
	private void addPlayerToTable(int position, Player player){
		
		table.getPlayers().add(position,player);
		
		if(player!=null){
			player.deposit(1000);
		}
		
	}
	
	private void drawButtons(Graphics g, int position, Player player){
		
		Point location = getPlayerPosition(position);
		int x = (int)location.getX()-35;
		int y = (int)location.getY()+10;
		// button size is 70 x 30
		JButton betButton = buttons.get(position).get("Bet");
		JButton hitButton = buttons.get(position).get("Hit");
		JButton standButton = buttons.get(position).get("Stand");
		JButton pushButton = buttons.get(position).get("Push");
		JButton removePlayerButton = buttons.get(position).get("Remove player");
		JButton addPlayerButton = buttons.get(position).get("Add player");
		JButton addBalance = buttons.get(position).get("Add balance");
		
		if(player!= null) {
			
			if(player.isAbleToBet()) {
				
				betButton.setVisible(true);
				betButton.setBounds(x, y,70,30);
				
			} else {
				
				betButton.setVisible(false);
				
			}
			y+=30;
			
			if(player.isAbleToHit()){
				
				hitButton.setVisible(true);
				hitButton.setBounds(x, y, 70, 30);
				
			} else {
				
				hitButton.setVisible(false);
				
			}
			y+=30;
			
			if(player.isAbleToStand()){
				
				standButton.setVisible(true);
				standButton.setBounds(x,y,70,30);
				
				
			} else {
				
				standButton.setVisible(false);
				
			}
			y+=40;
			
			
			if(player.isAbleToPush()){
				
				pushButton.setVisible(true);
				pushButton.setBounds(x,y,70,30);
				
			} else {
				
				pushButton.setVisible(false);
				
			}
			
			y+=40;
			x-=30;
			
			if(player.isRemovable()){
				
				removePlayerButton.setVisible(true);
				removePlayerButton.setBounds(x,y,130,30);
				
			} else {
				
				removePlayerButton.setVisible(false);
				
			}
			
			y+=40;

			addBalance.setVisible(true);
			addBalance.setBounds(x,y,130,30);
			
			addPlayerButton.setVisible(false);
			
		} else {
			
			
			
			betButton.setVisible(false);
			hitButton.setVisible(false);
			standButton.setVisible(false);
			pushButton.setVisible(false);
			removePlayerButton.setVisible(false);
			addBalance.setVisible(false);
			
			addPlayerButton.setVisible(true);
			addPlayerButton.setBounds(x-30, y-20,130,30);
			
		}
		
		
	}
	
	private void drawPlayer(Graphics g, int position, Player player){
		Graphics2D g2d = (Graphics2D)g;
		Point location = getPlayerPosition(position);
		int x = (int)location.getX();
		int y = (int)location.getY();
		
		Font f = new Font("Serif",Font.BOLD,30);
		Dimension textSize = getTextSize(player.toString(), g, f);
//		g.setColor(Color.BLUE);
//		g.drawRoundRect(x-(int)(textSize.getWidth()/2), y-(int)textSize.getHeight(), (int)textSize.getWidth(), (int)textSize.getHeight(), 5, 5);
		g.setColor(Color.BLACK);
		g.setFont(f);
		g.drawString(player.toString(), x-(int)(textSize.getWidth()/2), y);
		
		// draw line from players location to dealer's location
		// g.drawLine(x,y, getWidth()/2, 50);
		
		
		
		if(showResult && table.getCurrentGame().isOver() && player.isRegistered()){
			
			//System.out.println("Ivoked");
			
			PlayResult result = table.getCurrentGame().getPlayerResult(player);
			
			String resultName = null;
			
			Color resultColor = null;
			
			switch(result){
				case PUSH:
					resultColor = Color.BLACK; 
					resultName = "You pushed";
					break;
				case BLACK_JACK: 
					resultColor = Color.CYAN; 
					resultName = "BLACK JACK!!!";
					break;
				case LOST : 
					resultColor = Color.ORANGE; 
					resultName = "You lost";
					break;
				case WIN: 
					resultColor = Color.GREEN; 
					resultName = "You won";
					break;
				default:
					throw new RuntimeException("Unknown game outcome");
			}
			
			Dimension resultTextSize = getTextSize(resultName, g, f);
			
			g.setColor(resultColor);
			g.drawString(resultName, x-(int)(resultTextSize.getWidth()/2), (int)(y-textSize.getHeight()));

		}
		
		
		
		if(player.getNoOfCards() == 0)
			return;
		
		// draw player cards
		
		double bc = location.getY()-50;
		double ab = getWidth()/2 - location.getX();
		double ac = Math.sqrt(Math.pow(bc, 2) + Math.pow(ab, 2));
		
		AffineTransform original = g2d.getTransform();
		
		g2d.translate(getWidth()/2,50);
		g2d.rotate(Math.asin(ab/ac));
		g2d.translate(0,5*ac/9);
		
		drawCards(g2d, player);
		
		g2d.setTransform(original);
		
		
		
	}
	
	private void drawCards(Graphics g, Hand player) {
			
		int xPosition = 0;
		
		if(player.getNoOfCards() % 2 == 0){
			
			xPosition = getCardImage(player.getCard(0)).getWidth() * (player.getNoOfCards()/2 - 1) + CARD_DISTANCE / 2; 
			
		} else {
			
			xPosition = getCardImage(player.getCard(0)).getWidth()/2 + CARD_DISTANCE +  (player.getNoOfCards()/2 - 1) * (CARD_DISTANCE + getCardImage(player.getCard(0)).getWidth()); 
			
		}
	
		for(int i = 0 ; i < player.getNoOfCards() ; i++){
			
			Card card = player.getCard(i);
			
			BufferedImage cardImage = getCardImage(card);
			
			g.drawImage(cardImage,xPosition,0, this);
			
			xPosition -= (cardImage.getWidth()+CARD_DISTANCE);
			
		}
		
	}
	
	private Dimension getTextSize(String text, Graphics g, Font font){
		
		// get metrics from the graphics
		FontMetrics metrics = g.getFontMetrics(font);
		// get the height of a line of text in this
		// font and render context
		int hgt = metrics.getHeight();
		// get the advance of my text in this font
		// and render context
		int adv = metrics.stringWidth(text);
		// calculate the size of a box to hold the
		// text with some padding.
		return new Dimension(adv+2, hgt+2);

		
	}
	
	private Point getPlayerPosition(int index){
		int distance = getWidth() / 8;
		switch(index){
			
		case 0: return new Point(getX()+(7*distance),getHeight()-350);
		case 1: return new Point(getX()+(5*distance), getHeight()-250);
		case 2: return new Point(getX()+(3*distance), getHeight()-250);
		case 3: return new Point(getX()+(distance), getHeight()-350);
		default:
			throw new RuntimeException("No such table location "+index+" ;)");
		
		}
		
	}

	
	
	public static void main(String[] args) {
		
		
	    JFrame window = new JFrame();
	    window.setBounds(100, 100, 800, 500);
	    window.add(new MainWindow());
	    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // to exit the program when window is closed
	    window.setVisible(true);
	    
	}



	
}
