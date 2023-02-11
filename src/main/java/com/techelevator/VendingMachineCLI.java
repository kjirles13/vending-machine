package com.techelevator;

import com.techelevator.view.VendingMenu;

import java.io.FileNotFoundException;
import java.util.*;

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

    private String spacer = "\n========================================";
    private VendingMenu menu;

    public VendingMachineCLI(VendingMenu menu) {
        this.menu = menu;
    }

    public void run() {
        boolean running = true;
        double totalMoneyIn = 0.0;
        List<String> purchasedItemList = new ArrayList<>();
        TreeMap<String, Item> inventoryMap = null;

        try {
            inventoryMap = new TreeMap<>(menu.updateInventoy());
        } catch (FileNotFoundException ex) {
            System.out.println("Inventory file not found. Cannot start Vending Machine.");
            running = false;
        }

        System.out.println("\n======================================================");
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();

        }
        System.out.println(" *  Welcome to Aaron's and Kit's Vending Machine!  *");
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();

        }
        System.out.println("======================================================");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();

        }
        while (running) {
            System.out.println(spacer);
            String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

            if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
                System.out.println(spacer + "\n");
                displayInventory(inventoryMap);
            } else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
                boolean runningPurchaseMenu = true;

                while (runningPurchaseMenu) {
                    System.out.println(spacer);
                    System.out.println(String.format("Current balance: $%.2f %s", totalMoneyIn, spacer));

                    String purchaseChoice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);

                    if (purchaseChoice.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {
                        boolean runningFeedMoney = true;

                        while (runningFeedMoney) {
                            System.out.println(spacer);
                            System.out.println(String.format("Current balance: $%.2f%s", totalMoneyIn, spacer));
                            System.out.print("\nTo exit Feed Money menu, enter '0'\nFeed Money here >>> ");

                            String userInput = menu.userInputScanner();

                            if (userInput.equals("0")) {
                                runningFeedMoney = false;
                            }

                            try {
                                double moneyInserted = Double.parseDouble(userInput);
                                totalMoneyIn = menu.addToTotal(totalMoneyIn, moneyInserted);
                            } catch (NumberFormatException ex) {
                                System.out.println("\nOops that wasn't a number or '0'\nIf you want to leave this menu, enter '0'" + spacer);
                            }
                        }
                    } else if (purchaseChoice.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)) {
                        boolean runningSelectProduct = true;

                        while (runningSelectProduct) {
                            System.out.println(spacer + "\n");
                            displayInventory(inventoryMap);
                            System.out.println(String.format("%s\nCurrent balance: $%.2f%s", spacer, totalMoneyIn, spacer));
                            System.out.print("\nChoose your item >>> ");

                            String userInput = menu.userInputScanner().toUpperCase();

                            if (!inventoryMap.containsKey(userInput)) {
                                System.out.println(spacer + "\n\nThat is not a valid item.");
                            } else if (inventoryMap.get(userInput).getInventoryCount() == 0) {
                                System.out.println(spacer + "\n\nOops, that item is SOLD OUT! Please try again.");
                            } else if (totalMoneyIn < inventoryMap.get(userInput).getCost()) {
                                System.out.println(spacer + "\n\nSorry! Balance Too Low!");
                                break;
                            } else {
                                totalMoneyIn = makePurchase(inventoryMap, totalMoneyIn, userInput);
                                String phrase = inventoryMap.get(userInput).getPhrase();
                                String name = inventoryMap.get(userInput).getName();
                                double cost = inventoryMap.get(userInput).getCost();

                                purchasedItemList.add(userInput);
                                System.out.println(String.format("\nDispensing %s...", inventoryMap.get(userInput).getName()));
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException ex) {
                                    ex.printStackTrace();

                                }
                                System.out.println("<<<<RUMBLE>>>>");
                                try {
                                    Thread.sleep(300);
                                } catch (InterruptedException ex) {
                                    ex.printStackTrace();
                                }
                                System.out.println("<<<<RUMBLE>>>>");
                                try {
                                    Thread.sleep(300);
                                } catch (InterruptedException ex) {
                                    ex.printStackTrace();
                                }
                                System.out.println("<<<<RUMBLE>>>>");
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException ex) {
                                    ex.printStackTrace();
                                }
                                System.out.println(String.format("%s\n\n%s: $%.2f\n%s\n%s\nCurrent balance: $%.2f%s", spacer, name, cost, phrase, spacer, totalMoneyIn, spacer));
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException ex) {
                                    ex.printStackTrace();
                                }
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
                    } else if (purchaseChoice.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION)) {
                        List<Integer> moneyArrayList = new ArrayList<>(menu.getChange(totalMoneyIn));
                        displayPurchasedItems(purchasedItemList, inventoryMap, totalMoneyIn);
                        System.out.println(String.format("%s\n\nHere's your change:\n\nDollars: %s\nQuarters: %s\nDimes: %s\nNickels: %s\n\tTotal: $%.2f",
                                spacer,
                                moneyArrayList.get(0),
                                moneyArrayList.get(1),
                                moneyArrayList.get(2),
                                moneyArrayList.get(3),
                                totalMoneyIn
                        ));
                        totalMoneyIn = 0.00;

                        System.out.println(String.format("%s\nCurrent balance: $%.2f%s", spacer, totalMoneyIn, spacer));

                        runningPurchaseMenu = false;
                    }
                }
            } else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
                List<Integer> moneyArrayList = new ArrayList<>(menu.getChange(totalMoneyIn));

                System.out.println(String.format("%s\n\nHere's your change:\n\nDollars: %s\nQuarters: %s\nDimes: %s\nNickels %s\nTotal: $%.2f",
                        spacer,
                        moneyArrayList.get(0),
                        moneyArrayList.get(1),
                        moneyArrayList.get(2),
                        moneyArrayList.get(3),
                        totalMoneyIn
                ));

                System.out.println(String.format("%s\nThank you for using the Vending Machine!%s", spacer, spacer));

                running = false;
            }
        }
    }

    public static void main(String[] args) {
        VendingMenu menu = new VendingMenu(System.in, System.out);
        VendingMachineCLI cli = new VendingMachineCLI(menu);
        cli.run();
    }

    public void displayPurchasedItems(List<String> purchsaedItems, Map<String, Item> inventoryMap, double total) {
        System.out.println(String.format("%s\n\nHere are the item(s) you purchased:\n", spacer));

        for (String item : purchsaedItems) {
            String name = String.valueOf(inventoryMap.get(item).getName());
            double cost = inventoryMap.get(item).getCost();

            System.out.println(String.format("%s : $%.2f", name, cost));
        }
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

        return Double.parseDouble(String.valueOf(totalCurrentAmount));
    }
}

