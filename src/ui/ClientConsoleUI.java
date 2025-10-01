package ui;

public class ClientConsoleUI {
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


