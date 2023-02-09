package com.techelevator;

import java.math.BigDecimal;

public class Item {
    private String placeInVending;
    private BigDecimal costOfCandy;
    private String candyName;
    private int candyInventory;
    private String typeOfCandy;

    public Item(String placeInVending, BigDecimal costOfCandy, String candyName, int candyInventory, String typeOfCandy) {
        this.placeInVending = placeInVending;
        this.costOfCandy = costOfCandy;
        this.candyName = candyName;
        this.candyInventory = candyInventory;
        this.typeOfCandy = typeOfCandy;
    }

    public String getPlaceInVending() {
        return placeInVending;
    }

    public BigDecimal getCostOfCandy() {
        return costOfCandy;
    }

    public String getCandyName() {
        return candyName;
    }

    public int getCandyInventory() {
        return candyInventory;
    }

    public String getTypeOfCandy() {
        return typeOfCandy;
    }
}
