package com.techelevator.view;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.*;

import com.techelevator.Item;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MenuTest {

    private ByteArrayOutputStream output;
    private final double DELTA = 0.0001;

    @Before
    public void setup() {
        output = new ByteArrayOutputStream();
    }

    @Test
    public void displays_a_list_of_menu_options_and_prompts_user_to_make_a_choice() {
        Object[] options = new Object[]{Integer.valueOf(3), "Blind", "Mice"};
        VendingMenu menu = getMenuForTesting();

        menu.getChoiceFromOptions(options);

        String expected = System.lineSeparator() + "1) " + options[0].toString() + System.lineSeparator() + "2) " + options[1].toString() + System.lineSeparator() + "3) "
                + options[2].toString() + System.lineSeparator() + System.lineSeparator() + "Please choose an option >>> ";
        Assert.assertEquals(expected, output.toString());
    }

    @Test
    public void returns_object_corresponding_to_user_choice() {
        Integer expected = Integer.valueOf(456);
        Integer[] options = new Integer[]{Integer.valueOf(123), expected, Integer.valueOf(789)};
        VendingMenu menu = getMenuForTestingWithUserInput("2" + System.lineSeparator());

        Integer result = (Integer) menu.getChoiceFromOptions(options);

        Assert.assertEquals(expected, result);
    }

    @Test
    public void redisplays_menu_if_user_does_not_choose_valid_option() {
        Object[] options = new Object[]{"Larry", "Curly", "Moe"};
        VendingMenu menu = getMenuForTestingWithUserInput("4" + System.lineSeparator() + "1" + System.lineSeparator());

        menu.getChoiceFromOptions(options);

        String menuDisplay = System.lineSeparator() + "1) " + options[0].toString() + System.lineSeparator() + "2) " + options[1].toString() + System.lineSeparator() + "3) "
                + options[2].toString() + System.lineSeparator() + System.lineSeparator() + "Please choose an option >>> ";

        String expected = menuDisplay + System.lineSeparator() + "*** 4 is not a valid option ***" + System.lineSeparator() + System.lineSeparator() + menuDisplay;

        Assert.assertEquals(expected, output.toString());
    }

    @Test
    public void redisplays_menu_if_user_chooses_option_less_than_1() {
        Object[] options = new Object[]{"Larry", "Curly", "Moe"};
        VendingMenu menu = getMenuForTestingWithUserInput("0" + System.lineSeparator() + "1" + System.lineSeparator());

        menu.getChoiceFromOptions(options);

        String menuDisplay = System.lineSeparator() + "1) " + options[0].toString() + System.lineSeparator() + "2) " + options[1].toString() + System.lineSeparator() + "3) "
                + options[2].toString() + System.lineSeparator() + System.lineSeparator() + "Please choose an option >>> ";

        String expected = menuDisplay + System.lineSeparator() + "*** 0 is not a valid option ***" + System.lineSeparator() + System.lineSeparator() + menuDisplay;

        Assert.assertEquals(expected, output.toString());
    }

    @Test
    public void redisplays_menu_if_user_enters_garbage() {
        Object[] options = new Object[]{"Larry", "Curly", "Moe"};
        VendingMenu menu = getMenuForTestingWithUserInput("Mickey Mouse" + System.lineSeparator() + "1" + System.lineSeparator());

        menu.getChoiceFromOptions(options);

        String menuDisplay = System.lineSeparator() + "1) " + options[0].toString() + System.lineSeparator() + "2) " + options[1].toString() + System.lineSeparator() + "3) "
                + options[2].toString() + System.lineSeparator() + System.lineSeparator() + "Please choose an option >>> ";

        String expected = menuDisplay + System.lineSeparator() + "*** Mickey Mouse is not a valid option ***" + System.lineSeparator() + System.lineSeparator() + menuDisplay;

        Assert.assertEquals(expected, output.toString());
    }

    // Testing Money Handler class (Abstract)
    @Test
    public void getChange_should_return_accurate_amountsOf_moneyDenomination_back() {
        VendingMenu menu = getMenuForTesting();

        BigDecimal total = BigDecimal.valueOf(10.90);
        List<Integer> actualOutcome = menu.getChange(total);
        List<Integer> expectedOutcome = new ArrayList<Integer>(Arrays.asList(10, 3, 1, 1));

        Assert.assertEquals(actualOutcome, expectedOutcome);
    }

    @Test
    public void getChange_zeroInput_should_return_listOfZeroes() {
        VendingMenu menu = getMenuForTesting();

        BigDecimal total = BigDecimal.ZERO;
        List<Integer> actualOutcome = menu.getChange(total);
        List<Integer> expectedOutcome = new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0));

        Assert.assertEquals(actualOutcome, expectedOutcome);
    }

    @Test
    public void subtractFromTotal_should_subtract_costOfItem_from_total() {
        VendingMenu menu = getMenuForTesting();

        BigDecimal total = BigDecimal.valueOf(100);
        BigDecimal costOfItem = BigDecimal.valueOf(3.05);
        BigDecimal actualOutput = BigDecimal.valueOf(menu.subtractFromTotal(total, costOfItem));
        BigDecimal expectedOutput = BigDecimal.valueOf(96.95);

        Assert.assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void addToTotal_should_add_totalMoneyIn_and_userInput() {
        VendingMenu menu = getMenuForTesting();

        BigDecimal totalMoneyIn = BigDecimal.valueOf(10);
        BigDecimal userInput = BigDecimal.valueOf(5);
        BigDecimal actualOutput = menu.addToTotal(totalMoneyIn, userInput);
        BigDecimal expectedOutput = BigDecimal.valueOf(15);

        Assert.assertEquals(actualOutput, expectedOutput);
    }

    // Testing VendingMenu methods
    @Test
    public void makePurchase_should_subtract_cost_from_total() {
        VendingMenu menu = getMenuForTesting();
        BigDecimal total = BigDecimal.TEN;

        Map<String, Item> testInventoryMap = new TreeMap<>();
        testInventoryMap.put("A1", new Item("testItem1", 1.05, "type", 5));
        testInventoryMap.put("A2", new Item("testItem2", 1.05, "type", 4));
        testInventoryMap.put("A3", new Item("testItem3", 1.05, "type", 3));

        double actualOutput = menu.makePurchase(testInventoryMap, total, "A1");
        double expectedOutput = 8.95;

        Assert.assertEquals(expectedOutput, actualOutput, DELTA);
    }

    @Test
    public void makePurchase_should_decrement_inventory_count() {
        VendingMenu menu = getMenuForTesting();
        BigDecimal total = BigDecimal.TEN;

        Map<String, Item> testInventoryMap = new TreeMap<>();
        testInventoryMap.put("A1", new Item("testItem1", 1.05, "type", 5));
        testInventoryMap.put("A2", new Item("testItem2", 1.05, "type", 4));
        testInventoryMap.put("A3", new Item("testItem3", 1.05, "type", 3));

        menu.makePurchase(testInventoryMap, total, "A1");

        int actualOutput = testInventoryMap.get("A1").getInventoryCount();
        int expectedOutput = 4;

        Assert.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void updateInventory_inventory_map_should_be_right_size() throws FileNotFoundException {
        VendingMenu menu = getMenuForTesting();

        Map<String, Item> testInventoryMap = menu.updateInventoy();

        int actualOutput = testInventoryMap.size();
        int expectedOutput = 16;

        Assert.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void updateInventory_inventory_map_firstItem_should_match_file() throws FileNotFoundException {
        VendingMenu menu = getMenuForTesting();

        Map<String, Item> testInventoryMap = menu.updateInventoy();

        String actualOutputName = testInventoryMap.get("A1").getName();
        String expectedOutputName = "Potato Crisps";

        double actualOutputCost = testInventoryMap.get("A1").getCost();
        double expectedOutputCost = 3.05;

        String actualOutputType = testInventoryMap.get("A1").getType();
        String expectedOutputType = "Chip";

        Assert.assertEquals(expectedOutputName, actualOutputName);
        Assert.assertEquals(expectedOutputCost, actualOutputCost, DELTA);
        Assert.assertEquals(expectedOutputType, actualOutputType);
    }

    // Test writing to file
    @Test
    public void writeToFile_file_exists_should_return_true() {
        VendingMenu menu = getMenuForTesting();
        File file = new File("Log.txt");

        menu.writeToFile("GET CHANGE", BigDecimal.TEN, BigDecimal.ONE);
        menu.writeToFile("FEED MONEY", BigDecimal.TEN, BigDecimal.ONE);
        menu.writeToFile("Chip", BigDecimal.TEN, BigDecimal.ONE);

        Assert.assertTrue("The file does not exist", file.exists());
    }

    private VendingMenu getMenuForTestingWithUserInput(String userInput) {
        ByteArrayInputStream input = new ByteArrayInputStream(String.valueOf(userInput).getBytes());
        return new VendingMenu(input, output);
    }

    private VendingMenu getMenuForTesting() {
        return getMenuForTestingWithUserInput("1" + System.lineSeparator());
    }


}
