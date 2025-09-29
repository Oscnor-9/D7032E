package network;

import player.Player;
import player.HumanPlayer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

// Holds the listening socket AND accumulates joined players.
public class GameServer {
    private final ServerSocket serverSocket;
    private final List<Player> players = new ArrayList<>();

    public GameServer(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        System.out.println("🚀 Server listening on port " + port);
    }

    /** Blocks until a client connects; wraps it as a HumanPlayer and stores it. */
    public HumanPlayer acceptRemotePlayer(String name) throws IOException {
        System.out.println("🔌 Waiting for client...");
        Socket clientSocket = serverSocket.accept();
        System.out.println("✅ Client connected: " + clientSocket);
        HumanPlayer hp = HumanPlayer.remote(name, clientSocket); // <-- make sure HumanPlayer.remote exists
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
