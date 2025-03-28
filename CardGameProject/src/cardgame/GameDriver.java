package cardgame;

import java.util.*;
import java.io.*;
import javax.swing.*;

//This game uses a controller pattern - this is the controller class

public class GameDriver {
	
	private static GameDriver instance;		//Singleton pattern, ensure only one instance of the game
	
	
    public final static List<Player> players = new ArrayList<>(); //public so messager can see it
    private final List<Deck> decks = new ArrayList<>();
    private final List<Card> cardPack = new ArrayList<>();
    

    
    private GameDriver() {};
    
    public static synchronized GameDriver getInstance() {
    	if(instance == null) {
    		instance = new GameDriver();
    	}
    	return instance;
    }
    
    public void startGame() {
        int numPlayers = getNumPlayers();
        String packLocation = getPackLocation();

        try {
            loadCardPack(packLocation, numPlayers);
            initializePlayers(numPlayers);
            initializeHands();					//initialize hands before decks
            initializeDecks(numPlayers);
            assignDecks(numPlayers);
            // start threads for each player
            for (Player player : players) {
                player.start();
            }

            // wait for all players to finish, if game is over
        	for (Player player : players) {
        		player.join();
            	  
            }

            // write final deck contents to files
            for (Deck deck : decks) {
            	LogWriterFactory.getDeckLogger(deck).writeLog("final", -1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // method to get the number of players usin JOptionPane
    private int getNumPlayers() {
        int numPlayers = 0;
        Scanner scanner = null;

        try {
            //  get input via JOptionPane first
            String input = JOptionPane.showInputDialog(null, "Enter the number of players:");

            if (input != null && !input.isEmpty()) {
                try {
                    numPlayers = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.");
                    return getNumPlayers(); // Retry if invalid
                }
            } else {
                // moves input to terminal if JOptionPane is cancelled or empty, useful if they need to copy a filepath
                System.out.println("Please enter the number of players:");
                scanner = new Scanner(System.in);  // Initialize scanner here
                numPlayers = scanner.nextInt();
            }
        } finally {
            // close the scanner after use to prevent resource leak
            if (scanner != null) {
                scanner.close();
            }
        }

        return numPlayers;
    }

    // get the location of the card pack fie via JOptionPane)
    private String getPackLocation() {
        String packLocation = null;
        Scanner scanner = null;

        try {
            //  get input via JOptionPane first
            packLocation = JOptionPane.showInputDialog(null, "Enter the location of the card pack file:");

            if (packLocation == null || packLocation.isEmpty()) {
                System.out.println("Please enter the location of the card pack file:");
                scanner = new Scanner(System.in);  // Initialize scanner here
                packLocation = scanner.nextLine();
            }
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }

        return packLocation;
    }

    private void loadCardPack(String packLocation, int numPlayers) {
        try (Scanner scanner = new Scanner(new File(packLocation))) {
            while (scanner.hasNextInt()) {
                cardPack.add(new Card(scanner.nextInt()));
            }
            if (cardPack.size() != 8 * numPlayers) {
                throw new IllegalArgumentException("Pack file size mismatch");
            }
        } catch (FileNotFoundException e) {
        	System.out.println("Pack file not found");
        	System.exit(1);
            e.printStackTrace();
        }
    }

    private void initializeDecks(int numPlayers) {
        List<Card> tempDeck = new ArrayList<>(cardPack);
        for (int i = 0; i < numPlayers; i++) {
            decks.add(new Deck(i + 1, tempDeck.subList(i * 4, (i + 1) * 4))); 
            //there will be 4n cards left in the pack, now the hands have been dealt, hence *4
        }
    }

    private void initializePlayers(int numPlayers) {
    	if (numPlayers <= 1) {
    	    throw new IllegalArgumentException("Number of players must be greater than one.");
    	}

        for (int i = 0; i < numPlayers; i++) {
            players.add(new Player(i + 1));
            
            }
        }
    
    private void initializeHands() {
    	for(int i = 0; i < 4; i++) {
	    	for (Player player : players) {		//shorthand For loop to iterate through players
	    		player.dealCard(cardPack.remove(0));
	    	}    		
    	}
    }
    private void assignDecks(int numPlayers) {
    	for (int i = 0; i < numPlayers; i++) {
    		Player player = players.get(i);
    		player.setLeftDeck(decks.get(i));
    		player.setRightDeck(decks.get((i + 1) % numPlayers));
    	}
    }
    
}