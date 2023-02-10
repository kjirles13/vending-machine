package com.techelevator;

import com.techelevator.view.VendingMenu;

import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.text.DecimalFormat;
import java.util.*;

public class VendingMachineCLI {

    private final DecimalFormat DF = new DecimalFormat("0.00");

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
        double totalMoneyIn = 0.0;

        TreeMap<String, Item> inventoryMap = null;

        try {
            inventoryMap = new TreeMap<>(menu.updateInventoy());
        } catch (FileNotFoundException ex) {
            System.out.println("Inventory file not found. Cannot start Vending Machine.");
            running = false;
        }

        while (running) {
            String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

            // A switch statement could also be used here.  Your choice.
            if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
                displayInventory(inventoryMap);
            } else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
                boolean runningPurchaseMenu = true;

                while (runningPurchaseMenu) {
                System.out.println("\nCurrent Money Provided: $" + DF.format(totalMoneyIn));

                String purchaseChoice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);

                    if (purchaseChoice.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {
                        boolean runningFeedMoney = true;

                        while (runningFeedMoney) {
                            System.out.println("\nCurrent Money Provided: $" + DF.format(totalMoneyIn));
                            System.out.println("To exit Feed Money menu, enter '0'\n");
                            System.out.print("Feed Money here >>> ");

                            String userInput = menu.userInputScanner();

                            if (userInput.equals("0")) {
                                runningFeedMoney = false;
                            }

                            try {
                                totalMoneyIn += Double.parseDouble(userInput);
                                menu.feedMoney(totalMoneyIn);
                            } catch (NumberFormatException ex) {
                                System.out.println("Oops that wasn't a number or '0'\nIf you want to leave this menu, enter '0'\nTry again:");
                            }
                        }
                    } else if (purchaseChoice.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)) {
                        boolean runningSelectProduct = true;

                        //display items
                        while (runningSelectProduct) {
                            System.out.println();
                            displayInventory(inventoryMap);
                            System.out.println("\nYour total balance: $" + totalMoneyIn);
                            System.out.print("\nChoose your item >>> ");

                            String userInput = menu.userInputScanner();

                            if (!inventoryMap.containsKey(userInput)) {
                                System.out.println("\nThat is not a valid item");
                            } else if (inventoryMap.get(userInput).getInventoryCount() == 0) {
                                System.out.println("\nOops. That item is SOLD OUT. Please try again!");
                            } else if (totalMoneyIn < inventoryMap.get(userInput).getCost()) {
                                System.out.println("\nSorry! Balance Too Low!");
                            } else {
                                totalMoneyIn = makePurchase(inventoryMap, totalMoneyIn, userInput);
                                String phrase = inventoryMap.get(userInput).getPhrase();
                                String name = inventoryMap.get(userInput).getName();
                                double cost = inventoryMap.get(userInput).getCost();

                                System.out.println(String.format("\n%s: $%.2f \nNew balance: $%.2f\n%s", name, cost, totalMoneyIn, phrase));
                                System.out.print("\nWould you like to purchase another item? (Y/N) >>> ");

                                boolean yesNoSwitchCase = true;
                                while (yesNoSwitchCase) {
                                    userInput = menu.userInputScanner();
                                    switch (userInput.toUpperCase()) {
                                        case "Y":
                                            yesNoSwitchCase = false;
                                            break;
                                        case "N":
                                            runningSelectProduct = false;
                                            yesNoSwitchCase = false;
                                            break;
                                        default:
                                            System.out.print("\nOops. Not a valid input. Only use (Y/N).\nWould you like to purchase another item? (Y/N) >>> ");
                                            break;
                                    }
                                }
                            }
                        }

                    } else if (choice.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION)) {
                        //print out their item
                        //print out their new total
                        //take them back to main menu
                        runningPurchaseMenu = false;
                    }
                }
            } else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
                List<Integer> moneyArrayList = new ArrayList<>(menu.getChange(totalMoneyIn));

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
            System.out.println(String.format("%s : %s, $%.2f", item.getKey(), item.getValue().getName(), item.getValue().getCost()));
        }
    }

    public double makePurchase(Map<String, Item> inventoryMap, double totalMoney, String mapKey) {
        double totalCurrentAmount = 0;
        Item item = inventoryMap.get(mapKey);
        int inventoryCount = item.getInventoryCount();
        totalCurrentAmount = menu.subtractFromTotal(totalMoney, item.getCost());

        item.setInventoryCount(inventoryCount - 1);

        return Double.parseDouble(DF.format(totalCurrentAmount));
    }
}

