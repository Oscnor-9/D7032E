package main;

import card.Deck;
import card.GreenAppleCard;
import card.RedAppleCard;
import player.HumanPlayer;
import player.BotPlayer;
import player.Player;
import ui.ConsoleInput;
import ui.ConsoleUI;
import ui.NetworkInput;
import ui.NetworkUI;
import ui.ServerConsoleUI;
import game.Game;
import player.RemotePlayer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


/**
 * Starts a game server that hosts a local human player, bots, 
 * and a configurable number of remote players connected over TCP.
 */

public class ServerMain {

	/**
	 * Opens a server socket, waits for the requested number of remote players, 
	 * then starts a game session with all connected players
	 * @param greenDeck the deck of green apple cards
	 * @param redDeck the deck of red apple cards
	 * @param numOfBots number of bots
	 * @throws Exception if the server cannot be started
	 */
    public static void start(Deck<GreenAppleCard> greenDeck, Deck<RedAppleCard> redDeck, int numOfBots) throws Exception {
        ServerConsoleUI ui = new ServerConsoleUI();

        int numRemote = ui.askNumRemotePlayers();

        List<Player> players = new ArrayList<>();

        // Local host player
        players.add(new HumanPlayer("Host", new ConsoleUI(), new ConsoleInput()));

        // Bots
        for (int i = 1; i <= numOfBots; i++) {
            players.add(new BotPlayer("Bot" + i));
        }
        
        // 0 = ask OS for free port
        try (ServerSocket serverSocket = new ServerSocket(0)) {
            int port = serverSocket.getLocalPort();
            ui.showServerRunning(port, numRemote);

            // Wait until all remote players have connected
            for (int i = 1; i <= numRemote; i++) {
                Socket clientSocket = serverSocket.accept();
                ui.showRemoteConnected(i, numRemote);

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                RemotePlayer remote = new RemotePlayer(
                        "Remote" + i,
                        new NetworkUI(out),
                        new NetworkInput(in)
                );

                players.add(remote);
            }

            // ✅ Start game once all players are connected
            Game game = new Game(greenDeck, redDeck, players);
            game.start();
        }
    }
}
