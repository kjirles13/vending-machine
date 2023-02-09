package com.techelevator;

import java.math.BigDecimal;

public class Item {
    private double cost;
    private String name;
    private String type;
    private int inventoryCount;

    public Item(String name, double cost, String type, int inventoryCount) {
        this.name = name;
        this.cost = cost;
        this.type = type;
    }

    public int getInventoryCount() {
        return inventoryCount;
    }

    public double getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    private String getPhrase() {
        String phrase = "";
        switch (getType()) {
            case "Chip":
                phrase = "Crunch Crunch, It's Yummy!";
                break;
            case "Candy":
                phrase = "Munch Munch, Mmm Mmm Good!";
                break;
            case "Drink":
                phrase = "Glug Glug, Chug Chug!";
                break;
            case "Gum":
                phrase = "Chew Chew, Pop!";
                break;
        }
        return phrase;
    }
}
