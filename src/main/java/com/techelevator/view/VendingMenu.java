package com.techelevator.view;

import com.techelevator.Item;
import com.techelevator.MoneyHandler;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class VendingMenu extends MoneyHandler {

    private PrintWriter out;
    private Scanner in;
    private TreeMap<String, Item> itemMap = new TreeMap<>();
    private File logFile = new File("Log.txt");

    public VendingMenu(InputStream input, OutputStream output) {
        this.out = new PrintWriter(output);
        this.in = new Scanner(input);
    }

    public String userInputScanner() {
        String userInput = in.nextLine();
        return userInput;
    }

    public Object getChoiceFromOptions(Object[] options) {
        Object choice = null;
        while (choice == null) {
            displayMenuOptions(options);
            choice = getChoiceFromUserInput(options);
        }
        return choice;
    }

    private Object getChoiceFromUserInput(Object[] options) {
        Object choice = null;
        String userInput = in.nextLine();
        try {
            int selectedOption = Integer.valueOf(userInput);
            if (selectedOption > 0 && selectedOption <= options.length) {
                choice = options[selectedOption - 1];
            }
        } catch (NumberFormatException e) {
        }
        if (choice == null) {
            out.println(System.lineSeparator() + "*** " + userInput + " is not a valid option ***" + System.lineSeparator());
        }
        return choice;
    }

    private void displayMenuOptions(Object[] options) {
        out.println();
        for (int i = 0; i < options.length - 1; i++) {
            int optionNum = i + 1;
            out.println(optionNum + ") " + options[i]);
        }
        out.print(System.lineSeparator() + "Please choose an option >>> ");
        out.flush();
    }

    public double makePurchase(Map<String, Item> inventoryMap, double totalMoney, String mapKey) {
        double totalCurrentAmount = 0;
        Item item = inventoryMap.get(mapKey);
        int inventoryCount = item.getInventoryCount();
        totalCurrentAmount = subtractFromTotal(totalMoney, item.getCost());

        item.setInventoryCount(inventoryCount - 1);

        return Double.parseDouble(String.valueOf(totalCurrentAmount));
    }

    public TreeMap<String, Item> updateInventoy() throws FileNotFoundException {
        File inputFile = new File("vendingmachine.csv");
        Scanner fileScanner = new Scanner(inputFile);

        while (fileScanner.hasNextLine()) {
            String scannerLine = fileScanner.nextLine();
            String[] lineArray = scannerLine.split("\\|");
            Item item = new Item(lineArray[1], Double.parseDouble(lineArray[2]), lineArray[3], 5);
            itemMap.put(lineArray[0], item);
        }
        fileScanner.close();
        return itemMap;
    }

    public void writeToFile(String description, double originalAmountOrCost, double endingAmount) {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss a");
        String formattedDate = localDateTime.format(dateTimeFormatter);
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(logFile, logFile.exists()))) {
            if(!logFile.exists()){
                logFile.createNewFile();
            }
            writer.println(String.format("%s %s $%.2f $%.2f", formattedDate, description, originalAmountOrCost, endingAmount));
        } catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}