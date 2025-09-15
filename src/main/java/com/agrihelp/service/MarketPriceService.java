package com.agrihelp.service;

import com.agrihelp.model.MarketPriceCache;
import com.agrihelp.repository.MarketPriceCacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MarketPriceService {

    @Autowired
    private MarketPriceCacheRepository cacheRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ApiRetryUtil apiRetryUtil;  // ✅ Retry utility

    private static final String API_URL =
            "https://api.data.gov.in/resource/YOUR_RESOURCE_ID?api-key=YOUR_API_KEY&format=json&filters[commodity]={commodity}&filters[state]={state}";

    private static final int CACHE_VALIDITY_MINUTES = 30;

    /**
     * Get market price for a commodity in a state.
     * Uses cached data if available (valid for 30 mins), otherwise fetches from API with retries.
     */
    public MarketPriceCache getMarketPrice(String commodity, String state) {
        // 1️⃣ Check cache
        Optional<MarketPriceCache> cachedState = cacheRepository
                .findTopByCommodityIgnoreCaseAndStateIgnoreCaseOrderByLastUpdatedDesc(commodity, state);

        if (cachedState.isPresent() &&
                cachedState.get().getLastUpdated().isAfter(LocalDateTime.now().minusMinutes(CACHE_VALIDITY_MINUTES))) {
            return cachedState.get();
        }

        // 2️⃣ Fetch from API with retries
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> response = apiRetryUtil.getForObjectWithRetry(
                    restTemplate, API_URL, Map.class, commodity, state);

            MarketPriceCache latestPrice = buildMarketPriceFromResponse(response, commodity, state);

            if (latestPrice != null) {
                cacheRepository.save(latestPrice); // ✅ update cache
                return latestPrice;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 3️⃣ Fallback → last cached data or placeholder if API fails
        return cachedState.orElse(
                MarketPriceCache.builder()
                        .commodity(commodity)
                        .state(state)
                        .market("Unknown")
                        .price(0.0)
                        .unit("quintal")
                        .priceDate(LocalDate.now())
                        .lastUpdated(LocalDateTime.now())
                        .build()
        );
    }

    /**
     * Build MarketPriceCache object from API response
     */
    private MarketPriceCache buildMarketPriceFromResponse(Object response, String commodity, String state) {
        try {
            Map<?, ?> map = (Map<?, ?>) response;
            if (map.containsKey("records")) {
                List<?> records = (List<?>) map.get("records");
                if (!records.isEmpty()) {
                    Map<?, ?> firstRecord = (Map<?, ?>) records.get(0);

                    double price = Double.parseDouble(firstRecord.get("modal_price").toString());
                    String marketName = firstRecord.get("market").toString();
                    String dateStr = firstRecord.get("arrival_date").toString();

                    return MarketPriceCache.builder()
                            .commodity(commodity)
                            .state(state)
                            .market(marketName)
                            .price(price)
                            .unit("quintal")
                            .priceDate(LocalDate.parse(dateStr))
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
