package com.techelevator;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

/**************************************************************************************************************************
*  This is your Vending Machine Command Line Interface (CLI) class
*
*  It is the main process for the Vending Machine
*
*  THIS is where most, if not all, of your Vending Machine interactions should be coded
*  
*  It is instantiated and invoked from the VendingMachineApp (main() application)
*  
*  Your code vending machine related code should be placed in here
***************************************************************************************************************************/
import com.techelevator.view.Menu;         // Gain access to Menu class provided for the Capstone

public class VendingMachineCLI {
	
	TreeMap<String, HashMap<Item, Integer>> inventory = new TreeMap<>();
	
	private static final Integer MAX_STOCK = 5;
	
    // Main menu options defined as constants

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE      = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT          = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS,
													    MAIN_MENU_OPTION_PURCHASE,
													    MAIN_MENU_OPTION_EXIT
													    };
	
	private Menu vendingMenu;              // Menu object to be used by an instance of this class
	
	public VendingMachineCLI(Menu menu) {  // Constructor - user will pas a menu for this class to use
		this.vendingMenu = menu;           // Make the Menu the user object passed, our Menu
	}
	/**************************************************************************************************************************
	*  VendingMachineCLI main processing loop
	*  
	*  Display the main menu and process option chosen
	*
	*  It is invoked from the VendingMachineApp program
	*
	*  THIS is where most, if not all, of your Vending Machine objects and interactions 
	*  should be coded
	*
	*  Methods should be defined following run() method and invoked from it
	 * @throws IOException 
	*
	***************************************************************************************************************************/

	public void run() throws IOException {
		restock();

		boolean shouldProcess = true;         // Loop control variable
		
		while(shouldProcess) {                // Loop until user indicates they want to exit
			
			String choice = (String)vendingMenu.getChoiceFromOptions(MAIN_MENU_OPTIONS);  // Display menu and get choice
			
			switch(choice) {                  // Process based on user menu choice
			
				case MAIN_MENU_OPTION_DISPLAY_ITEMS:
					displayItems();           // invoke method to display items in Vending Machine
					break;                    // Exit switch statement
			
				case MAIN_MENU_OPTION_PURCHASE:
					purchaseItems();          // invoke method to purchase items from Vending Machine
					break;                    // Exit switch statement
			
				case MAIN_MENU_OPTION_EXIT:
					endMethodProcessing();    // Invoke method to perform end of method processing
					shouldProcess = false;    // Set variable to end loop
					break;                    // Exit switch statement
			}	
		}
		return;                               // End method and return to caller
	}
/********************************************************************************************************
 * Methods used to perform processing
 * @throws IOException 
 ********************************************************************************************************/
	
	public void restock() throws IOException {
		File restockFile = new File("vendingmachine.csv");
		Scanner stock = new Scanner(restockFile);
		
		String line;
		HashMap<Item, Integer> items;
		
		while(stock.hasNextLine()) {
			items = new HashMap<>();
			
			line = stock.nextLine();
			String[] splitLine = line.split("\\|");
			
			Double price = Double.parseDouble(splitLine[2]);
			
			Item itemForSale = new Item(splitLine[1], price, splitLine[3]);
			
			items.clear(); 							// Clear map so previous items don't get placed in wrong slot
			items.put(itemForSale, MAX_STOCK);
			
			inventory.put(splitLine[0], items);
		}
		System.out.println("Map: " + inventory);
		System.out.println("VENDING MACHINE HAS BEEN RESTOCKED");
		stock.close();
	}
	
	public void displayItems() throws IOException {      // static attribute used as method is not associated with specific object instance
		this.restock();
		BufferedReader restockFile = new BufferedReader(
				new FileReader("vendingmachine.csv"));
		String line;
		Integer itemStock = 0;;
		while((line=restockFile.readLine())!=null) {
			HashMap<Item, Integer> items= inventory.get(line.substring(0, 2));
			Set<Item> itemSet = items.keySet();
			
			for(Item anItem : itemSet) {
				itemStock = items.get(anItem);
			}
			//itemStock = items.get(itemSet);
			
			line = line.replace("Chip", "") + itemStock;
			line = line.replace("Drink", "");
			line = line.replace("Candy", "");
			line = line.replace("Gum", "");
			//System.out.println(inventory);
			//System.out.println(inventory.get(line.substring(0, 2)));
			System.out.println(line);
		}
		restockFile.close();
	}	

	
	
	public void purchaseItems() {	 // static attribute used as method is not associated with specific object instance
		// Code to purchase items from Vending Machine
	}
	
	public void endMethodProcessing() { // static attribute used as method is not associated with specific object instance
		// Any processing that needs to be done before method ends
	}
}
