package network;

import player.HumanPlayer;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {
    private final ServerSocket serverSocket;

    public GameServer(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    /** Blocks until a new client connects, then returns it wrapped as a HumanPlayer */
    public HumanPlayer waitForRemotePlayer(String name) throws IOException {
        System.out.println("🔌 Waiting for client...");
        Socket clientSocket = serverSocket.accept();
        System.out.println("✅ Client connected: " + clientSocket);
        return HumanPlayer.remote(name, clientSocket);
    }
}
