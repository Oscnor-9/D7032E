package ui;

import java.util.Scanner;
import java.util.Map;

/**
 * Console-based user interface for remote clients.
 * <p>
 * Handles text input and output between the user and the connected game server.
 */
public class ClientConsoleUI {
	    private final Scanner scanner = new Scanner(System.in);
	    
	    /**
	     * Reads a full line of user input from the console
	     * @return trimmed user input, or an empty input string if none is entered
	     */
	    public String readUserInput() {
	        if (scanner.hasNextLine()) {
	            return scanner.nextLine().trim();
	        }
	        return "";
	    }
	
	    /**
	     * Prompts the user for a server IP, defaulting to {@code localhost}
	     * @return the entered host or {@code localhost} if blank
	     */
	    public String askServerIp() {
	        System.out.print("Enter server IP (default = localhost): ");
	        String host = scanner.nextLine().trim();
	        if (host.isEmpty()) {
	            host = "localhost";
	        }
	        return host;
	    }
	
	    /**
	     * Prompts the user for a server port number
	     * @return the port number as an int
	     */
	    public int askServerPort() {
	        System.out.print("Enter server port: ");
	        return scanner.nextInt();
	    }
	    
	    /**
	     * Display a successful connection message
	     * @param host server hostname
	     * @param port server port number
	     */
	    public void showConnected(String host, int port) {
	        System.out.println("Connected to server " + host + ":" + port);
	    }
	    
	    /**
	     * Display a generic server message
	     */
	    public void showMessage(String msg) {
	        System.out.println("SERVER: " + msg);
	    }

	    /**
	     * Prompts the player to choose a card to play
	     */
	    public void showPlayerTurn() {
	        System.out.print("Your turn! Choose index: ");
	    }

	    /**
	     * Notifies the player that they are the judge this round
	     */
	    public void showJudgeTurn() {
	        System.out.println("You are the judge! Cards to choose from:");
	    }

	    /**
	     * Prompts the judge to pick a winning card
	     */
	    public void showJudgePrompt() {
	        System.out.print("Pick winning card index: ");
	    }
	    
	    /**
	     * Display the current scores for all players
	     * @param scores map of player names to their score values
	     */
	    public void showScores(Map<String, Integer> scores) {
	        System.out.println("Current scores:");
	        scores.forEach((name, score) -> 
	            System.out.println("   " + name + " → " + score)
	        );
	    }
	}


