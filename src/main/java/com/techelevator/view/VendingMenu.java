package com.techelevator.view;

import com.techelevator.Item;
import com.techelevator.MoneyHandler;

import java.io.*;
import java.util.Scanner;
import java.util.TreeMap;

public class VendingMenu extends MoneyHandler {

	private PrintWriter out;
	private Scanner in;
	private TreeMap<String, Item> itemMap = new TreeMap<>();

	public VendingMenu(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output);
		this.in = new Scanner(input);
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
			// eat the exception, an error message will be displayed below since choice will be null
		}
		if (choice == null) {
			out.println(System.lineSeparator() + "*** " + userInput + " is not a valid option ***" + System.lineSeparator());
		}
		return choice;
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

	private void displayMenuOptions(Object[] options) {
		out.println();
		for (int i = 0; i < options.length; i++) {
			int optionNum = i + 1;
			out.println(optionNum + ") " + options[i]);
		}
		out.print(System.lineSeparator() + "Please choose an option >>> ");
		out.flush();
	}
	public String userInputScanner(){
		String userInput = in.nextLine();
		return userInput;
	}
}
