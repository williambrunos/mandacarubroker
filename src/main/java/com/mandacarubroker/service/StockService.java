package com.mandacarubroker.service;

import com.mandacarubroker.domain.stock.RequestStockDTO;
import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.domain.stock.StockRepository;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service class for handling operations related to stocks.
 */
@Service
public class StockService {

    /**
     * Stock repository.
     */
    private final StockRepository stockRepository;

    /**
     * Constructs a new StockService with the given StockRepository.
     * @param stockRepo The repository for accessing stock data.
     */
    public StockService(final StockRepository stockRepo) {
        this.stockRepository = stockRepo;
    }

    /**
     * Retrieves all stocks from the repository.
     * @return A list of all stocks.
     */
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    /**
     * Retrieves a stock by its ID.
     * @param id The ID of the stock to retrieve.
     * @return An Optional containing the stock, or empty if not found.
     */
    public Optional<Stock> getStockById(final String id) {
        return stockRepository.findById(id);
    }

    /**
     * Creates a new stock based on the provided data.
     * @param data The data for creating the new stock.
     * @return The newly created stock.
     */
    public Stock createStock(final RequestStockDTO data) {
        Stock newStock = new Stock(data);
        validateRequestStockDTO(data);
        return stockRepository.save(newStock);
    }

    /**
     * Updates an existing stock with the provided ID and data.
     * @param id The ID of the stock to update.
     * @param updatedStock The updated data for the stock.
     * @return An Optional containing the updated stock, or empty if not found.
     */
    public Optional<Stock> updateStock(final String id, final Stock updatedStock) {
        return stockRepository.findById(id)
                .map(stock -> {
                    stock.setSymbol(updatedStock.getSymbol());
                    stock.setCompanyName(updatedStock.getCompanyName());
                    double newPrice = stock.changePrice(updatedStock.getPrice(), true);
                    stock.setPrice(newPrice);
                    return stockRepository.save(stock);
                });
    }

    /**
     * Deletes a stock with the provided ID.
     * @param id The ID of the stock to delete.
     */
    public void deleteStock(final String id) {
        stockRepository.deleteById(id);
    }

    /**
     * Validates the RequestStockDTO object.
     * @param data The RequestStockDTO object to validate.
     * @throws ConstraintViolationException If validation fails.
     */
    public static void validateRequestStockDTO(final RequestStockDTO data) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<RequestStockDTO>> violations = validator.validate(data);

        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Validation failed. Details: ");

            for (ConstraintViolation<RequestStockDTO> violation : violations) {
                errorMessage.append(String.format("[%s: %s], ", violation.getPropertyPath(), violation.getMessage()));
            }

            errorMessage.delete(errorMessage.length() - 2, errorMessage.length());

            throw new ConstraintViolationException(errorMessage.toString(), violations);
        }
    }

    /**
     * Validates the RequestStockDTO object and creates a new stock if validation passes.
     * @param data The RequestStockDTO object to validate and create a stock from.
     */
    public void validateAndCreateStock(final RequestStockDTO data) {
        validateRequestStockDTO(data);
        Stock newStock = new Stock(data);
        stockRepository.save(newStock);
    }
}
