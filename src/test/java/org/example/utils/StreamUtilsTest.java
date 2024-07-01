package org.example.utils;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.example.utils.StreamUtils.getTextFromInputStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StreamUtilsTest {

    @Test
    void getTextFromInputStreamTest() throws IOException {
        String initialString = "Some test";
        InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());
        assertEquals(initialString, getTextFromInputStream(targetStream));
    }
}