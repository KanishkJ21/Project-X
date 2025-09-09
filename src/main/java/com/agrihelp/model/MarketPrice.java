package com.agrihelp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "market_prices")
public class MarketPrice {

    @Id
    private String id;
    private String commodity;
    private String market;
    private double price;
    private LocalDateTime timestamp;

    public MarketPrice() {}

    public MarketPrice(String commodity, String market, double price, LocalDateTime timestamp) {
        this.commodity = commodity;
        this.market = market;
        this.price = price;
        this.timestamp = timestamp;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCommodity() { return commodity; }
    public void setCommodity(String commodity) { this.commodity = commodity; }

    public String getMarket() { return market; }
    public void setMarket(String market) { this.market = market; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
