package ui;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Reads input from a network socket connected to a remote client.
 * <p>
 * Implements {@link ChoiceInput} to integrate with remote players
 */
public class NetworkInput implements ChoiceInput {
    private final BufferedReader in;

    /**
     * Creates a new input reader from a socket stream
     * @param in a buffered reader connected to the client socket
     */
    public NetworkInput(BufferedReader in) {
        this.in = in;
    }

    @Override
    public String readLine() throws IOException {
        return in.readLine();
    }
}
