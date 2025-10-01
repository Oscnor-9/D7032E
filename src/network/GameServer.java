package network;

import player.Player;
import ui.NetworkInput;
import player.HumanPlayer;
import ui.NetworkUI;
import ui.ServerLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import ui.GameUI;

// Holds the listening socket AND accumulates joined players.
public class GameServer {
    private final ServerSocket serverSocket;
    private final List<Player> players = new ArrayList<>();

    public GameServer(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        ServerLogger.info("🚀 Listening on port " + port);
    }

    /** Blocks until a client connects; wraps it as a HumanPlayer and stores it. */
    public HumanPlayer acceptRemotePlayer(String name) throws IOException {
        ServerLogger.info("Waiting for client...");
        Socket clientSocket = serverSocket.accept();
        ServerLogger.success("Client connected: " + clientSocket);

        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out   = new PrintWriter(clientSocket.getOutputStream(), true);

        GameUI ui = new NetworkUI(out);       // output only
        NetworkInput input = new NetworkInput(in);  // input only

        HumanPlayer hp = new HumanPlayer(name, ui, input);  // now 'name' is valid
        players.add(hp);
        return hp;
    }


    /** Optionally add local/bot players to the same list. */
    public void addPlayer(Player p) {
        players.add(p);
    }

    /** Returns a COPY of current players (so callers can’t mutate internal list). */
    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }
}
