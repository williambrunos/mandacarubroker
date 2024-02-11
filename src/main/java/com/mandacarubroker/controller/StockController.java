package com.mandacarubroker.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
     * The logger service used for Observability  purposes
     */
    private static final Logger logger = LoggerFactory.getLogger(StockController.class);

    public StockController(final StockService service) {
        this.stockService = service;
    }

    @GetMapping
    public List<Stock> getAllStocks() {
        logger.info("Retrieving all stocks");
        return stockService.getAllStocks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStockById(@PathVariable final String id) {
        logger.info("Retrieving stock with ID: {}", id);
        return stockService.getStockById(id)
                .map(stock -> ResponseEntity.ok().body(stock))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Stock> createStock(@RequestBody final RequestStockDTO data) {
        logger.info("Creating new stock");
        Stock createdStock = stockService.createStock(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStock);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateStock(@PathVariable final String id, @RequestBody final Stock updatedStock) {
        logger.info("Updating stock with ID: {}", id);
        if (id == null || id.trim().isEmpty()) {
            logger.error("Invalid ID provided for update operation");
            return ResponseEntity.badRequest().build();
        }

        stockService.updateStock(id, updatedStock);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStockById(@PathVariable final String id) {
        logger.info("Deleting stock with ID: {}", id);
        if (id == null || id.trim().isEmpty()) {
            logger.error("Invalid ID provided for deletion operation");
            return ResponseEntity.badRequest().build();
        }

        stockService.deleteStock(id);
        return ResponseEntity.ok().build();
    }
}
