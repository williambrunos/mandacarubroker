package com.mandacarubroker.controller;

import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.domain.stock.RequestStockDTO;
import com.mandacarubroker.service.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Controller class to handle HTTP requests related to stocks.
 */
@RestController
@RequestMapping("/stocks")
public class StockController {

    /**
     * The StockService used by this controller to perform operations on stocks.
     */
    private final StockService stockService;

    /**
     * Constructs a new StockController with the provided StockService.
     *
     * @param service the StockService to be used by the controller
     */
    public StockController(final StockService service) {
        this.stockService = service;
    }

    /**
     * Retrieves all stocks.
     *
     * @return a list of all stocks
     */
    @GetMapping
    public List<Stock> getAllStocks() {
        return stockService.getAllStocks();
    }

    /**
     * Retrieves a stock by its ID.
     *
     * @param id the ID of the stock to retrieve
     * @return the stock with the given ID
     * @throws ResponseStatusException if the stock with the given ID is not found
     */
    @GetMapping("/{id}")
    public Stock getStockById(@PathVariable final String id) {
        return stockService.getStockById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Stock not found"));
    }

    /**
     * Creates a new stock.
     *
     * @param data the data for the new stock
     * @return the newly created stock
     */
    @PostMapping
    public ResponseEntity<Stock> createStock(@RequestBody final RequestStockDTO data) {
        Stock createdStock = stockService.createStock(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStock);
    }

    /**
     * Updates an existing stock.
     *
     * @param id           the ID of the stock to update
     * @param updatedStock the updated information for the stock
     * @return the updated stock, or 404 code if no stock with the given ID is found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateStock(@PathVariable final String id, @RequestBody final Stock updatedStock) {
        if (id == null || id.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        stockService.updateStock(id, updatedStock);
        return ResponseEntity.ok().build();
    }

    /**
     * Deletes a stock by its ID.
     *
     * @param id the ID of the stock to delete
     * @return a ResponseEntity indicating the success of the deletion operation or a 404 code if the ID was not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStockById(@PathVariable final String id) {
        if (id == null || id.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        stockService.deleteStock(id);
        return ResponseEntity.ok().build();
    }

}
