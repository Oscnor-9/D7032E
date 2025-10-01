package io;

import card.Card;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class FileCardLoader {

    public static <T extends Card> ArrayList<T> loadCards(
            String fileName,
            Function<String, T> factory) throws IOException {

        Path filePath = Path.of(fileName).toAbsolutePath();

        if (!Files.exists(filePath)) {
            throw new IOException("❌ File not found: " + filePath);
        }

        List<String> lines = Files.readAllLines(filePath, Charset.forName("Cp1252"));
        ArrayList<T> cards = new ArrayList<>();

        for (String line : lines) {
            String trimmed = line.trim();
            if (!trimmed.isEmpty()) {
                cards.add(factory.apply(trimmed));
            }
        }

        return cards;
    }
}
