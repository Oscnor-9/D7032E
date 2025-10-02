package ui;

import java.util.Scanner;

public class ClientConsoleUI {
	    private final Scanner scanner = new Scanner(System.in);
	    
	    public String readUserInput() {
	        // consume the leftover newline if nextInt was called before
	        if (scanner.hasNextLine()) {
	            return scanner.nextLine().trim();
	        }
	        return "";
	    }
	
	    public String askServerIp() {
	        System.out.print("Enter server IP (default = localhost): ");
	        String host = scanner.nextLine().trim();
	        if (host.isEmpty()) {
	            host = "localhost";
	        }
	        return host;
	    }
	
	    public int askServerPort() {
	        System.out.print("Enter server port: ");
	        return scanner.nextInt();
	    }
	    public void showConnected(String host, int port) {
	        System.out.println("✅ Connected to server " + host + ":" + port);
	    }

	    public void showMessage(String msg) {
	        System.out.println("SERVER: " + msg);
	    }

	    public void showPlayerTurn() {
	        System.out.print("👉 Your turn! Choose index: ");
	    }

	    public void showJudgeTurn() {
	        System.out.println("👑 You are the judge! Cards to choose from:");
	    }

	    public void showJudgePrompt() {
	        System.out.print("Pick winning card index: ");
	    }
	}


