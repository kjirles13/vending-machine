package com.techelevator.view;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MenuTest {

	private ByteArrayOutputStream output;

	@Before
	public void setup() {
		output = new ByteArrayOutputStream();
	}

	@Test
	public void displays_a_list_of_menu_options_and_prompts_user_to_make_a_choice() {
		Object[] options = new Object[] { Integer.valueOf(3), "Blind", "Mice" };
		VendingMenu menu = getMenuForTesting();

		menu.getChoiceFromOptions(options);

		String expected = System.lineSeparator() + "1) " + options[0].toString() + System.lineSeparator() + "2) " + options[1].toString() + System.lineSeparator() + "3) "
				+ options[2].toString() + System.lineSeparator() + System.lineSeparator() + "Please choose an option >>> ";
		Assert.assertEquals(expected, output.toString());
	}

	@Test
	public void returns_object_corresponding_to_user_choice() {
		Integer expected = Integer.valueOf(456);
		Integer[] options = new Integer[] { Integer.valueOf(123), expected, Integer.valueOf(789) };
		VendingMenu menu = getMenuForTestingWithUserInput("2" + System.lineSeparator());

		Integer result = (Integer) menu.getChoiceFromOptions(options);

		Assert.assertEquals(expected, result);
	}

	@Test
	public void redisplays_menu_if_user_does_not_choose_valid_option() {
		Object[] options = new Object[] { "Larry", "Curly", "Moe" };
		VendingMenu menu = getMenuForTestingWithUserInput("4" + System.lineSeparator() + "1" + System.lineSeparator());

		menu.getChoiceFromOptions(options);

		String menuDisplay = System.lineSeparator() + "1) " + options[0].toString() + System.lineSeparator() + "2) " + options[1].toString() + System.lineSeparator() + "3) "
				+ options[2].toString() + System.lineSeparator() + System.lineSeparator() + "Please choose an option >>> ";

		String expected = menuDisplay + System.lineSeparator() + "*** 4 is not a valid option ***" + System.lineSeparator() + System.lineSeparator() + menuDisplay;

		Assert.assertEquals(expected, output.toString());
	}

	@Test
	public void redisplays_menu_if_user_chooses_option_less_than_1() {
		Object[] options = new Object[] { "Larry", "Curly", "Moe" };
		VendingMenu menu = getMenuForTestingWithUserInput("0" + System.lineSeparator() + "1" + System.lineSeparator());

		menu.getChoiceFromOptions(options);

		String menuDisplay = System.lineSeparator() + "1) " + options[0].toString() + System.lineSeparator() + "2) " + options[1].toString() + System.lineSeparator() + "3) "
				+ options[2].toString() + System.lineSeparator() + System.lineSeparator() + "Please choose an option >>> ";

		String expected = menuDisplay + System.lineSeparator() + "*** 0 is not a valid option ***" + System.lineSeparator() + System.lineSeparator() + menuDisplay;

		Assert.assertEquals(expected, output.toString());
	}

	@Test
	public void redisplays_menu_if_user_enters_garbage() {
		Object[] options = new Object[] { "Larry", "Curly", "Moe" };
		VendingMenu menu = getMenuForTestingWithUserInput("Mickey Mouse" + System.lineSeparator() + "1" + System.lineSeparator());

		menu.getChoiceFromOptions(options);

		String menuDisplay = System.lineSeparator() + "1) " + options[0].toString() + System.lineSeparator() + "2) " + options[1].toString() + System.lineSeparator() + "3) "
				+ options[2].toString() + System.lineSeparator() + System.lineSeparator() + "Please choose an option >>> ";

		String expected = menuDisplay + System.lineSeparator() + "*** Mickey Mouse is not a valid option ***" + System.lineSeparator() + System.lineSeparator() + menuDisplay;

		Assert.assertEquals(expected, output.toString());
	}

	

	//Testing Money Handler class (Abstract)
	@Test
	public void getChange_Should_Return_Accurate_AmountsOf_MoneyDenomination_Back(){
		VendingMenu menu = getMenuForTesting();

		BigDecimal total = BigDecimal.valueOf(10.90);
		List actualOutcome = menu.getChange(total);
		List<Integer> expectedOutcome = new ArrayList<Integer>(Arrays.asList(10, 3, 1, 1));

		Assert.assertEquals(actualOutcome, expectedOutcome);

		total = BigDecimal.ZERO;
		actualOutcome = menu.getChange(total);
		expectedOutcome = new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0));

		Assert.assertEquals(actualOutcome, expectedOutcome);
	}

	@Test
	public void subTractFromTotal_Should_Subtract_CostofItem_From_Total(){
		VendingMenu menu = getMenuForTesting();

		BigDecimal total = BigDecimal.valueOf(100);
		BigDecimal costOfItem = BigDecimal.valueOf(3.05);
		BigDecimal actualOutput = BigDecimal.valueOf(menu.subtractFromTotal(total, costOfItem));
		BigDecimal expectedOutput = BigDecimal.valueOf(96.95);

		Assert.assertEquals(actualOutput, expectedOutput);
	}

	@Test
	public void addToTotal_Should_Add_totalMoneyIn_And_userInput(){
		VendingMenu menu = getMenuForTesting();

		BigDecimal totalMoneyIn = BigDecimal.valueOf(10);
		BigDecimal userInput = BigDecimal.valueOf(5);
		BigDecimal actualOutput = menu.addToTotal(totalMoneyIn, userInput);
		BigDecimal expectedOutput = BigDecimal.valueOf(15);

		Assert.assertEquals(actualOutput, expectedOutput);
	}

	private VendingMenu getMenuForTestingWithUserInput(String userInput) {
		ByteArrayInputStream input = new ByteArrayInputStream(String.valueOf(userInput).getBytes());
		return new VendingMenu(input, output);
	}

	private VendingMenu getMenuForTesting() {
		return getMenuForTestingWithUserInput("1" + System.lineSeparator());
	}



}
