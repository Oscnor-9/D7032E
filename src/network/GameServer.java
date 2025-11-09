package network;

import player.Player;
import player.RemotePlayer;   // ✅ use RemotePlayer instead of HumanPlayer
import ui.NetworkInput;
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

/**
 * TCP game server that listens for incoming client connections
 * and wraps them as {@link RemotePlayer} instances.
 * <p>
 * The server gathers all players (remote, local and bots) so that
 * a {@link game.Game} instace can be started 
 */
public class GameServer {
    private final ServerSocket serverSocket;
    private final List<Player> players = new ArrayList<>();
   
    /** 
     * Creates a server that listen to the given port
     * @param port the port
     * @throws IOException if the server socket can't be opened
     */
    public GameServer(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        ServerLogger.info("🚀 Listening on port " + port);
    }

    /**
     * Waits until all clients are connected, then wraps 
     * the connection as a {@link RemotePlayer}
     * @param name the displayed name to assign to the remote player
     * @return the created {@link RemotePlayer}
     * @throws IOException if accepting the client or creating stream fails
     */
    public RemotePlayer acceptRemotePlayer(String name) throws IOException {
        ServerLogger.info("Waiting for client...");
        Socket clientSocket = serverSocket.accept();
        ServerLogger.success("Client connected: " + clientSocket);

        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out   = new PrintWriter(clientSocket.getOutputStream(), true);

        NetworkUI ui = new NetworkUI(out);       // server -> client
        NetworkInput input = new NetworkInput(in);  // client -> server

        RemotePlayer rp = new RemotePlayer(name, ui, input);
        players.add(rp);
        return rp;
    }

    /**
     * Adds a local player or a bot to the game
     * @param p the player to add
     */
    public void addPlayer(Player p) {
        players.add(p);
    }

    /**
     * Returns a copy of the current players
     * @return a new list containing all players
     */
    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }
}
