package com.agrihelp.controller;

import com.agrihelp.model.MarketPriceCache;
import com.agrihelp.service.MarketPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/market")
public class MarketPriceController {

    @Autowired
    private MarketPriceService marketService;

    /**
     * Get market prices for a commodity in a given state.
     * Example: GET /api/market/wheat/Delhi
     */
    @GetMapping("/{commodity}/{state}")
    public MarketPriceCache getMarketPrice(
            @PathVariable String commodity,
            @PathVariable String state
    ) {
        return marketService.getMarketPrice(commodity, state);
    }

    /**
     * Health check endpoint (useful to test API is running).
     * Example: GET /api/market/ping
     */
    @GetMapping("/ping")
    public String ping() {
        return "MarketPrice API is running 🚀";
    }
}
