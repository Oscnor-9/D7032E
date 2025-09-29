package network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GameClient {
    private String host;
    private int port;

    public GameClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception {
        try (Socket socket = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to server!");

            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("SERVER: " + line);

                // respond with console input
                if (line.startsWith("YOUR_TURN") || line.startsWith("JUDGE_TURN")) {
                    System.out.print("Choose index: ");
                    String choice = console.readLine();
                    out.println(choice);
                }
            }
        }
    }
}
