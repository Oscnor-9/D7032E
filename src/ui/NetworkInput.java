package ui;

import java.io.BufferedReader;
import java.io.IOException;

public class NetworkInput implements ChoiceInput {
    private final BufferedReader in;

    public NetworkInput(BufferedReader in) {
        this.in = in;
    }

    @Override
    public String readLine() throws IOException {
        return in.readLine();
    }
}
