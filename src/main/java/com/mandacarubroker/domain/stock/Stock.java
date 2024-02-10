package com.mandacarubroker.domain.stock;

import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Class representing a stock.
 */
@Table(name = "stock")
@Entity(name = "stock")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Stock {

    /**
     * Unique identifier for the stock.
     */
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /**
     * Symbol of the stock.
     */
    private String symbol;

    /**
     * Company name associated with the stock.
     */
    private String companyName;

    /**
     * Current price of the stock.
     */
    private double price;

    /**
     * Constructor initializing a Stock instance based on data provided by a RequestStockDTO object.
     * @param requestStockDTO Object containing stock data.
     */
    public Stock(final RequestStockDTO requestStockDTO) {
        this.symbol = requestStockDTO.symbol();
        this.companyName = requestStockDTO.companyName();
        this.price = 0;
        this.price = changePrice(requestStockDTO.price(), true);
    }

    /**
     * Method to change the stock price.
     * @param amount Amount to be changed in the stock price.
     * @param increase Boolean indicating whether the price is increasing or decreasing.
     * @return The new stock price.
     */
    public double changePrice(final double amount, final boolean increase) {
        if (increase) {
            return increasePrice(amount);
        } else {
            return decreasePrice(amount);
        }
    }

    /**
     * Method to increase the stock price.
     * @param amount Value to be added to the current stock price.
     * @return The new stock price after the increase.
     */
    public double increasePrice(final double amount) {
        return this.price + amount;
    }

    /**
     * Method to decrease the stock price.
     * @param amount Value to be subtracted from the current stock price.
     * @return The new stock price after the decrease.
     */
    public double decreasePrice(final double amount) {
        return this.price - amount;
    }
}
