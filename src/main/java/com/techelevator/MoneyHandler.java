package com.techelevator;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public abstract class MoneyHandler {
    private final BigDecimal NICKEL = BigDecimal.valueOf(0.05);
    private final BigDecimal DIME = BigDecimal.valueOf(0.10);
    private final BigDecimal QUARTER = BigDecimal.valueOf(0.25);
    private final BigDecimal DOLLAR = BigDecimal.valueOf(1.00);

    public BigDecimal countMoney(double total, double newAmount) {
        BigDecimal newTotal = BigDecimal.valueOf(total);
        newTotal.add(BigDecimal.valueOf(newAmount));
        return newTotal;
    }

   /* DecimalFormat df = new DecimalFormat("0.00");

        return df.format(amount.multiply(conversionRate));*/


}
