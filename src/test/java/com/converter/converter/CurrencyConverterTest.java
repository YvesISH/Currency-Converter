package com.converter.converter;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.apache.http.HttpEntity;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CurrencyConverterTest {

    @Mock
    private HttpClient mockHttpClient;

    private CurrencyConverter currencyConverter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        currencyConverter = new CurrencyConverter();
    }

    @Test
    void testGetExchangeRates() throws IOException {
        // Arrange
        currencyConverter.setHttpClient(mockHttpClient);

        HttpResponse mockResponse = mock(HttpResponse.class);
        when(mockHttpClient.execute(any(HttpGet.class))).thenReturn(mockResponse);

        String mockJsonResponse = "{ \"rates\": { \"USD\": 1.0, \"EUR\": 0.85 } }";
        ByteArrayInputStream mockInputStream = new ByteArrayInputStream(
                mockJsonResponse.getBytes(StandardCharsets.UTF_8));
        when(mockResponse.getEntity().getContent()).thenReturn(mockInputStream);

        // Act
        Map<String, Double> exchangeRates = currencyConverter.getExchangeRates("USD");

        // Assert
        assertEquals(2, exchangeRates.size());
        assertEquals(1.0, exchangeRates.get("USD"));
        assertEquals(0.85, exchangeRates.get("EUR"));

        // Verify that execute method was called with the correct argument
        verify(mockHttpClient, times(1)).execute(any(HttpGet.class));
    }

    @Test
    void testConvert() throws IOException {
        // Arrange
        currencyConverter.setHttpClient(mockHttpClient);

        // For testGetExchangeRates method
        HttpResponse mockResponse = mock(HttpResponse.class);
        when(mockHttpClient.execute(any(HttpGet.class))).thenReturn(mockResponse);

        HttpEntity mockEntity = mock(HttpEntity.class);
        when(mockResponse.getEntity()).thenReturn(mockEntity);

        String mockJsonResponse = "{ \"rates\": { \"USD\": 1.0, \"EUR\": 0.85 } }";
        ByteArrayInputStream mockInputStream = new ByteArrayInputStream(
                mockJsonResponse.getBytes(StandardCharsets.UTF_8));
        when(mockEntity.getContent()).thenReturn(mockInputStream);


        // Act
        double convertedAmount = currencyConverter.convert(100.0, "USD", "EUR");

        // Assert
        assertEquals(85.0, convertedAmount, 0.001);

        // Verify that execute method was called with the correct argument
        verify(mockHttpClient, times(1)).execute(any(HttpGet.class));
    }
}
