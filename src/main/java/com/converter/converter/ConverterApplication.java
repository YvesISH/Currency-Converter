package com.converter.converter;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;

@SpringBootApplication
public class ConverterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConverterApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(CurrencyConverter currencyConverter) {
		return args -> {
			Scanner scanner = new Scanner(System.in);

			// Get user input for currency conversion
			System.out.println("Enter the base currency code: ");
			String baseCurrency = scanner.nextLine();

			System.out.println("Enter the target currency code: ");
			String targetCurrency = scanner.nextLine();

			System.out.println("Enter the amount to convert: ");
			double amount = scanner.nextDouble();

			System.out.println("Fetching exchange rates...");
			System.out.println("Exchange rates: " + currencyConverter.getExchangeRates(baseCurrency));

			double convertedAmount = currencyConverter.convert(amount, baseCurrency, targetCurrency);

			if (convertedAmount != -1) {
				System.out.println(amount + " " + baseCurrency + " is equivalent to " +
						convertedAmount + " " + targetCurrency);
			}
		};
	}
}
