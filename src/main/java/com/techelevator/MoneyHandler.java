package com.techelevator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class MoneyHandler {
    private final int NICKEL = 5;
    private final int DIME = 10;
    private final int QUARTER = 25;
    private final int DOLLAR = 100;

    public double subtractFromTotal(double total, double costOfItem) {
        BigDecimal change = BigDecimal.valueOf(total).subtract(BigDecimal.valueOf(costOfItem));
        return Double.parseDouble(String.valueOf(change));
    }

    public double addToTotal(double totalMoneyIn, double userInput) {
        BigDecimal newTotal = BigDecimal.valueOf(totalMoneyIn).add(BigDecimal.valueOf(userInput));
        return Double.parseDouble(String.valueOf(newTotal));
    }

    public List<Integer> getChange(double total) {
        total *= 100;
        int dollarCount = 0, quarterCount = 0, dimeCount = 0, nickelCount = 0;
        List<Integer> changeConstants = new ArrayList<>(Arrays.asList(DOLLAR, QUARTER, DIME, NICKEL));
        List<Integer> changeList = new ArrayList<>(Arrays.asList(dollarCount, quarterCount, dimeCount, nickelCount));

        try {
            for (int i = 0; i < changeList.size(); i++) {
                int count = (int) (total / changeConstants.get(i));
                changeList.set(i, count);
                total %= changeConstants.get(i);

            }
        } catch (ArithmeticException ex) {
            return changeList;
        }
        return changeList;
    }
}
