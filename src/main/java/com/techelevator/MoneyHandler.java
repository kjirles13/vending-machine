package com.techelevator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class MoneyHandler {
    private final BigDecimal NICKEL = BigDecimal.valueOf(5);
    private final BigDecimal DIME = BigDecimal.valueOf(10);
    private final BigDecimal QUARTER = BigDecimal.valueOf(25);
    private final BigDecimal DOLLAR = BigDecimal.valueOf(100);

    public double subtractFromTotal(BigDecimal total, BigDecimal costOfItem) {
        BigDecimal change = total.subtract(costOfItem);
        return Double.parseDouble(String.valueOf(change));
    }

    public BigDecimal addToTotal(BigDecimal totalMoneyIn, BigDecimal userInput) {
        BigDecimal newTotal = totalMoneyIn.add(userInput);
        return newTotal;
    }

    public List<Integer> getChange(BigDecimal total) {
        BigDecimal convertedTotal = total.multiply(BigDecimal.valueOf(100.0));
        int dollarCount = 0, quarterCount = 0, dimeCount = 0, nickelCount = 0;
        List<BigDecimal> changeConstants = new ArrayList<>(Arrays.asList(DOLLAR, QUARTER, DIME, NICKEL));
        List<Integer> changeList = new ArrayList<>(Arrays.asList(dollarCount, quarterCount, dimeCount, nickelCount));

        try {
            for (int i = 0; i < changeList.size(); i++) {
                int count = (int) convertedTotal.divide(changeConstants.get(i)).toBigInteger().intValue();;
                changeList.set(i, count);
                convertedTotal = convertedTotal.remainder(changeConstants.get(i));
            }
        } catch (ArithmeticException ex) {
            System.out.println("Error with getChange");
            return changeList;
        }
        return changeList;
    }
}
