package com.converter.converter;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TestUtils {

    public static String runApplication(Runnable application) {
        // Redirect System.out to capture output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Run the application
        application.run();

        // Reset System.out to the original stream
        System.setOut(System.out);

        return outputStream.toString().trim();
    }
}
