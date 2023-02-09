package com.techelevator;

import com.techelevator.view.VendingMenu;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VendingMachineCLI {

    private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
    private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
    private static final String MAIN_MENU_OPTION_EXIT = "Exit";
    private static final String MAIN_MENU_SECRET_OPTION = "*Sales Report";

    private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
    private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
    private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";

    private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT, MAIN_MENU_SECRET_OPTION};
    private static final String[] PURCHASE_MENU_OPTIONS = {PURCHASE_MENU_OPTION_FEED_MONEY, PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_OPTION_FINISH_TRANSACTION};

    private VendingMenu menu;

    public VendingMachineCLI(VendingMenu menu) {
        this.menu = menu;
    }

    public void run() {
        boolean running = true;

        // Create vending machine object and set inventory
        VendingMachine vendingMachine = new VendingMachine();
        double totalMoneyIn = 0.0;
        Map<String, Item> inventoryMap = null;
        try {
            inventoryMap = new HashMap<>(vendingMachine.updateInventoy());
        } catch (FileNotFoundException ex) {
            System.out.println("Inventory file not found. Cannot start Vending Machine");
            running = false;
        }

        while (running) {
            String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

            // A switch statement could also be used here.  Your choice.
            if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
                displayInventory(inventoryMap);
            } else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
                // do purchase
                String purchaseChoice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);
                if (choice.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {

                } else if (choice.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)) {
//                    switch (choice) {
//                        case "1":
//                            System.out.print("Please deposit money >>> " );
//
//
//                            System.out.println(vendingMachine.countMoney());
//                            break;
//                        case "2":
//                            displayInventory(inventoryMap);
//                            break;
//                        case "3":
//
//
//                            break;

                } else if (choice.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION)) {
                    //print out their item
                    //print out their new total
                    //take them back to main menu
                }
            } else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
                List<Integer> moneyArrayList = new ArrayList<>(vendingMachine.getChange(5.75));
                System.out.println(String.format("\nHere's your change:\n\nDollars: %s\nQuarters: %s\nDimes: %s\nNickels %s\nTotal: %s", moneyArrayList.get(0), moneyArrayList.get(1), moneyArrayList.get(2), moneyArrayList.get(3), totalMoneyIn));
                System.out.println("\nThank you for using the Vending Machine!");
                running = false;
            }

        }
    }

    public static void main(String[] args) {
        VendingMenu menu = new VendingMenu(System.in, System.out);
        VendingMachineCLI cli = new VendingMachineCLI(menu);
        cli.run();
    }

    public void displayInventory(Map<String, Item> inventoryMap) {
        for (Map.Entry<String, Item> item : inventoryMap.entrySet()) {
            System.out.println(String.format("%s : %s, $%s", item.getKey(), item.getValue().getName(), item.getValue().getCost()));
        }
    }
}
