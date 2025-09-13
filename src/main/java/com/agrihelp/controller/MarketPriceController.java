package com.agrihelp.controller;

import com.agrihelp.model.MarketPrice;
import com.agrihelp.service.MarketPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/market")
public class MarketPriceController {

    @Autowired
    private MarketPriceService marketService;

    // Get real-time market prices for a commodity and state/market
    @GetMapping("/{commodity}/{state}")
    public MarketPrice getMarketPrice(
            @PathVariable String commodity,
            @PathVariable String state
    ) {
        return marketService.getMarketPrice(commodity, state);
    }
}
