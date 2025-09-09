package com.agrihelp.service;

import com.agrihelp.model.MarketPrice;
import com.agrihelp.repository.MarketPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class MarketPriceService {

    @Autowired
    private MarketPriceRepository marketPriceRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final String API_URL = "https://api.example.com/marketprice?commodity={commodity}";

    @SuppressWarnings("unchecked")
    public MarketPrice fetchAndSavePrice(String commodity) {
        Map<String, Object> response = restTemplate.getForObject(API_URL, Map.class, commodity);

        if (response == null) return null;

        String market = (String) response.get("market");
        double price = Double.parseDouble(response.get("price").toString());

        MarketPrice marketPrice = new MarketPrice(commodity, market, price, LocalDateTime.now());
        return marketPriceRepository.save(marketPrice);
    }

    public List<MarketPrice> getPricesByCommodity(String commodity) {
        return marketPriceRepository.findByCommodity(commodity);
    }

    public List<MarketPrice> getAllPrices() {
        return marketPriceRepository.findAll();
    }
}
