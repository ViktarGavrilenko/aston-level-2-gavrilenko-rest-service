package org.example.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class StreamUtils {
    private StreamUtils() {
    }

    public static String getTextFromInputStream(InputStream stream) throws IOException {
        Scanner scanner = new Scanner(stream, StandardCharsets.UTF_8);
        String text = scanner.useDelimiter("\\A").next();
        scanner.close();
        return text;
    }
}