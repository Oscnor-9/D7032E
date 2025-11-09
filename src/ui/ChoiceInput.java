package ui;

import java.io.IOException;

/**
 * Interface representing an input source that can read
 * a single line of user input
 * <p>
 * Used by classes that require player choices or responses
 */
public interface ChoiceInput {
	/*
	 * Reads a line of text input
	 * @return the line read as a string
	 * @throws IOException if the input stream is closed or unavailable
	 */
    String readLine() throws IOException;
}
