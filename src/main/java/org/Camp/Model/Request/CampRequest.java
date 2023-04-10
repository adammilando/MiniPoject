package org.Camp.Model.Request;

import java.math.BigDecimal;

public class CampRequest {
    private String name;
    private String location;
    private BigDecimal price;
    private int stock;

    public CampRequest() {
    }

    public CampRequest(String name, String location, BigDecimal price, int stock) {
        this.name = name;
        this.location = location;
        this.price = price;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
