package com.converter.converter;

import com.converter.converter.CurrencyConverter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConverterApplicationTest {

    @Test
    void testCurrencyConversion() throws Exception {
        // Create a mock of CurrencyConverter
        CurrencyConverter currencyConverter = Mockito.mock(CurrencyConverter.class);

        // Set up a test scenario
        Mockito.when(currencyConverter.getExchangeRates("USD")).thenReturn(null);
        Mockito.when(currencyConverter.convert(100.0, "USD", "EUR")).thenReturn(80.0);

        // Create an instance of ConverterApplication
        ConverterApplication converterApplication = new ConverterApplication();

        // Mock user input
        String userInput = "USD\nEUR\n100.0\n";
        InputStream inputStream = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(inputStream);

        // Run the application
        converterApplication.run(currencyConverter).run();
    }
}