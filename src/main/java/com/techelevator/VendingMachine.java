package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class VendingMachine extends MoneyHandler {

    public Map<String, Item> updateInventoy() throws FileNotFoundException {
        File inputFile = new File("vendingmachine.csv");
        Scanner fileScanner = new Scanner(inputFile);
        Map<String, Item> itemMap = new HashMap<>();

        while (fileScanner.hasNextLine()) {
            String scannerLine = fileScanner.nextLine();
            String[] lineArray = scannerLine.split("\\|");
            Item item = new Item(lineArray[1], Double.valueOf(lineArray[2]), lineArray[3], 5);
            itemMap.put(lineArray[0], item);
        }
        fileScanner.close();
        return itemMap;
    }

}
