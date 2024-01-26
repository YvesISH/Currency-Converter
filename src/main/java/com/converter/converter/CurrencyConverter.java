package com.converter.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Component
public class CurrencyConverter {

    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/";
    private HttpClient httpClient;

    // Method to set the HttpClient
    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Map<String, Double> getExchangeRates(String baseCurrency) {
        if (httpClient == null) {
            httpClient = HttpClients.createDefault();
        }

        HttpGet request = new HttpGet(API_URL + baseCurrency);

        try {
            HttpResponse response = httpClient.execute(request);

            if (response.getEntity() == null) {
                System.out.println("No response entity");
                return null;
            }

            InputStream inputStream = response.getEntity().getContent();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(inputStream);

            JsonNode ratesNode = rootNode.path("rates");
            Map<String, Double> exchangeRates = new HashMap<>();

            ratesNode.fields().forEachRemaining(entry -> {
                exchangeRates.put(entry.getKey(), entry.getValue().asDouble());
            });

            return exchangeRates;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public double convert(double amount, String fromCurrency, String toCurrency) {
        Map<String, Double> exchangeRates = getExchangeRates(fromCurrency);

        if (exchangeRates != null && exchangeRates.containsKey(toCurrency)) {
            double rate = exchangeRates.get(toCurrency);
            return amount * rate;
        } else {
            System.out.println("Invalid currency codes");
            return -1;
        }
    }
}
