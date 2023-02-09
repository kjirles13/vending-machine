package com.techelevator;

import java.math.BigDecimal;

public class Item {
    private String slotLocation;
    private BigDecimal cost;
    private String name;
    private int amount;
    private String type;

    public Item(String slotLocation, BigDecimal cost, String name, int amount, String type) {
        this.slotLocation = slotLocation;
        this.cost = cost;
        this.name = name;
        this.amount = amount;
        this.type = type;
    }

    public String getSlotLocation() {
        return slotLocation;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
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
