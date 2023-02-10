package com.techelevator;

import com.sun.source.tree.Tree;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class VendingMachine extends MoneyHandler {
    private TreeMap<String, Item> itemMap = new TreeMap<>();

    public TreeMap<String, Item> updateInventoy() throws FileNotFoundException {
        File inputFile = new File("vendingmachine.csv");
        Scanner fileScanner = new Scanner(inputFile);


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
