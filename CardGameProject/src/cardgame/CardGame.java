package cardgame;

import java.util.*;
import java.io.*;
import javax.swing.*;

public class CardGame {
    private static List<Player> players = new ArrayList<>();
    private static List<CardDeck> decks = new ArrayList<>();
    private static List<Card> cardPack = new ArrayList<>();

    public static void main(String[] args) {
        int numPlayers = getNumPlayers();
        String packLocation = getPackLocation();

        try {
            loadCardPack(packLocation, numPlayers);
            initializePlayers(numPlayers);
            initializeHands();					//initialize hands before decks
            initializeDecks(numPlayers);
            assignDecks(numPlayers);
            // Start the threads for each player
            for (Player player : players) {
                player.start();
            }

            // Wait for all players to finish, if game is over
        	for (Player player : players) {
        		player.join();
            	  
            }

            // Write final deck contents to files
            for (CardDeck deck : decks) {
                deck.writeDeckToFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to get the number of players (via command line or JOptionPane)
    private static int getNumPlayers() {
        int numPlayers = 0;
        Scanner scanner = null;

        try {
            // Try to get input via JOptionPane first
            String input = JOptionPane.showInputDialog(null, "Enter the number of players:");

            if (input != null && !input.isEmpty()) {
                try {
                    numPlayers = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.");
                    return getNumPlayers(); // Retry if invalid
                }
            } else {
                // Fallback to command-line input if JOptionPane is cancelled or empty
                System.out.println("Please enter the number of players:");
                scanner = new Scanner(System.in);  // Initialize scanner here
                numPlayers = scanner.nextInt();
            }
        } finally {
            // Always close the scanner after use to prevent resource leak
            if (scanner != null) {
                scanner.close();
            }
        }

        return numPlayers;
    }

    // Method to get the location of the card pack file (via command line or JOptionPane)
    private static String getPackLocation() {
        String packLocation = null;
        Scanner scanner = null;

        try {
            // Try to get input via JOptionPane first
            packLocation = JOptionPane.showInputDialog(null, "Enter the location of the card pack file:");

            if (packLocation == null || packLocation.isEmpty()) {
                // Fallback to command-line input if JOptionPane is cancelled or empty
                System.out.println("Please enter the location of the card pack file:");
                scanner = new Scanner(System.in);  // Initialize scanner here
                packLocation = scanner.nextLine();
            }
        } finally {
            // Always close the scanner after use to prevent resource leak
            if (scanner != null) {
                scanner.close();
            }
        }

        return packLocation;
    }

    private static void loadCardPack(String packLocation, int numPlayers) {
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

    private static void initializeDecks(int numPlayers) {
        List<Card> tempDeck = new ArrayList<>(cardPack);
        for (int i = 0; i < numPlayers; i++) {
            decks.add(new CardDeck(i + 1, tempDeck.subList(i * 4, (i + 1) * 4))); 
            //there will be 4n cards left in the pack, now the hands have been dealt, hence *4
        }
    }

    private static void initializePlayers(int numPlayers) {
        for (int i = 0; i < numPlayers; i++) {
            players.add(new Player(i + 1));
            
            }
        }
    
    private static void initializeHands() {
    	for(int i = 0; i < 4; i++) {
	    	for (Player player : players) {		//shorthand For loop to iterate through players
	    		player.dealCard(cardPack.remove(0));
	    	}    		
    	}
    }
    private static void assignDecks(int numPlayers) {
    	for (int i = 0; i < numPlayers; i++) {
    		Player player = players.get(i);
    		player.setLeftDeck(decks.get(i));
    		player.setRightDeck(decks.get((i + 1) % numPlayers));
    	}
    }
    
}