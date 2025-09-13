package com.agrihelp.service;

import com.agrihelp.model.MarketPrice;
import com.agrihelp.repository.MarketPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class MarketPriceService {

    @Autowired
    private MarketPriceRepository marketPriceRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final String API_URL =
            "https://api.data.gov.in/resource/YOUR_RESOURCE_ID?api-key=YOUR_API_KEY&format=json&filters[commodity]={commodity}&filters[state]={state}";

    /**
     * Get real-time market price for crop and state
     */
    public MarketPrice getMarketPrice(String crop, String state) {
        try {
            // Call external API
            @SuppressWarnings("unchecked")
            Map<String, Object> response = restTemplate.getForObject(
                    API_URL, Map.class, crop, state
            );

            // Convert API response → MarketPrice object
            MarketPrice latestPrice = buildMarketPriceFromResponse(response, crop, state);

            if (latestPrice != null) {
                // Save to DB (cache)
                marketPriceRepository.save(latestPrice);
                return latestPrice;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Fallback: return last cached price from MongoDB
        return marketPriceRepository
                .findTopByCropNameIgnoreCaseAndLocationIgnoreCaseOrderByLastUpdatedDesc(crop, state)
                .orElse(null);
    }

    /**
     * Build MarketPrice object from API JSON response
     */
    private MarketPrice buildMarketPriceFromResponse(Object response, String crop, String state) {
        try {
            Map<?, ?> map = (Map<?, ?>) response;
            if (map.containsKey("records")) {
                List<?> records = (List<?>) map.get("records");
                if (!records.isEmpty()) {
                    Map<?, ?> firstRecord = (Map<?, ?>) records.get(0);

                    double price = Double.parseDouble(firstRecord.get("modal_price").toString());
                    String marketName = firstRecord.get("market").toString(); // ✅ now used
                    String dateStr = firstRecord.get("arrival_date").toString(); // usually "yyyy-MM-dd"

                    return MarketPrice.builder()
                            .cropName(crop)
                            .location(state)
                            .marketName(marketName)          // ✅ set market name
                            .price(price)
                            .unit("quintal")
                            .priceDate(LocalDate.parse(dateStr)) // LocalDate
                            .lastUpdated(LocalDateTime.now())
                            .build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
