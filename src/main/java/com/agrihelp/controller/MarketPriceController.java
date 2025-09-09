package com.agrihelp.controller;

import com.agrihelp.model.MarketPrice;
import com.agrihelp.service.MarketPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/market")
public class MarketPriceController {

    @Autowired
    private MarketPriceService marketPriceService;

    // Fetch real-time price and save
    @GetMapping("/fetch")
    public MarketPrice fetchPrice(@RequestParam String commodity) {
        return marketPriceService.fetchAndSavePrice(commodity);
    }

    // Get all prices for a commodity
    @GetMapping("/commodity")
    public List<MarketPrice> getPricesByCommodity(@RequestParam String commodity) {
        return marketPriceService.getPricesByCommodity(commodity);
    }

    // Get all market prices stored
    @GetMapping("/all")
    public List<MarketPrice> getAllPrices() {
        return marketPriceService.getAllPrices();
    }
}
