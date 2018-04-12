/*
 * Copyright (c) 2018 by Jordan Pickett and Nora White. All rights reserved.
 *
 * Apr 11, 2018
 * 
 */
package CPSC3620;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The Class main.
 *
 * @author Jordan Pickett and Nora White
 */
public class main {
	
	private static final long MEGABYTE = 1024L * 1024L;

	/**
	 * Bytes to megabytes.
	 *
	 * @param bytes the bytes
	 * @return the double
	 */
	public static double bytesToMegabytes(double bytes) {
		return bytes / MEGABYTE;

	}

	/**
	 * The main method.This controls the main loop as well
	 * as the logic for the users decisions.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception {
		int[] decision = {-1, -1};
		File rbFile = null;
		
		while(true)
		{
			decision = consoleUI();
			rbFile = null;
			
			if(decision[0] == 4 || decision[1] == 8)
			{
				System.out.println("Good Bye!");
				System.exit(0);
			}
			
			switch(decision[1]) {
			// File Size 100
			case 1:
				rbFile = new File("res/randomList100.txt");
				break;
			// File Size 500
			case 2:
				rbFile = new File("res/randomList500.txt");
				break;
			// File Size 1000
			case 3:
				rbFile = new File("res/randomList1000.txt");
				break;
			// File Size 2500
			case 4:
				rbFile = new File("res/randomList2500.txt");
				break;
			// File Size 5000
			case 5:
				rbFile = new File("res/randomList5000.txt");
				break;
			// File Size 7500
			case 6:
				rbFile = new File("res/randomList7500.txt");
				break;
			// File Size 10000
			case 7:
				rbFile = new File("res/randomList10000.txt");
				break;
			//Default should never be reached
			default:
				System.out.println("Program Ended with Error");
				System.exit(-1);
				break;
			}
			
			switch(decision[0]) {
			//Insertion
			case 1:
				rbInsert(rbFile, true);
				break;
			//Deletion
			case 2:
				rbDelete(rbFile);
				break;
			//Search
			case 3:
				rbSearch(rbFile);
				break;
			//Default should never be reached
			default:
				System.out.println("Program Ended with Error");
				System.exit(-1);
				break;	
			}
		}
	}
	
	/**
	 * Red Black Insert. A method that inserts a certain amount of elements into
	 * the red black tree. The method then either prints out the time it took to
	 * complete the operations or it doesn't. The method then returns the Red Black
	 * Tree with all of the elements that have been inserted.
	 *
	 * @param rbFile the file that contains the inserts
	 * @param isPrint whether or not the times should be printed
	 * @return the red black tree with elements inserted
	 * @throws FileNotFoundException the file not found exception
	 */
	private static RedBlackTree<Integer> rbInsert(File rbFile, Boolean isPrint) throws FileNotFoundException {
		
		//Create the RedBlackTree that will; be used
		RedBlackTree<Integer> rbt = new RedBlackTree<Integer>();
		
		//Create a scanner on the file
		Scanner sc = new Scanner(rbFile);
		
		//Initialize the timing variables
		long totalTime = 0, startTime = 0, endTime = 0;
		
		int count = 0;
	
		// Insert Loop with printing
		if(isPrint) {
			// Insert Loop with printing
			Runtime rt = Runtime.getRuntime();
			// Delete Loop
			while (sc.hasNextLine()) {
				startTime = System.nanoTime();
				rbt.search(sc.nextInt());
				endTime = System.nanoTime();
				totalTime += (endTime - startTime);
				count++;
			}
			
			//Print out the results
			System.out.println("\n***Insertion of " + count + " elements***");
			System.out.println("Total Time (ns): " + totalTime + "\nAverage Time (ns): " + totalTime / count);
			rt.gc();
			long memory = rt.totalMemory() - rt.freeMemory();
			System.out.println("Used memory in bytes: " + memory);
			System.out.println("Used memory in megabytes: " + bytesToMegabytes(memory));
		}
		else {
			while (sc.hasNextLine()) {
				rbt.insert(sc.nextInt());
			}
		}
		return rbt;
	}
	
	/**
	 * Red Black delete. A method that deletes a certain amount of elements from
	 * the red black tree. The method then prints out the time it took to complete
	 * all of the operations.
	 *
	 * @param rbFile the file with the numbers being removed
	 * @throws FileNotFoundException the file not found exception
	 */
	private static void rbDelete(File rbFile) throws FileNotFoundException {
		RedBlackTree<Integer> rbt = new RedBlackTree<Integer>();
		rbt = rbInsert(rbFile, false);
		int count = 0;
		
		//Create a scanner on the file
		Scanner sc = new Scanner(rbFile);
		
		//Initialize the timing variables
		long totalTime = 0, startTime = 0, endTime = 0;
		
		// Insert Loop with printing
		Runtime rt = Runtime.getRuntime();
		
		// Delete Loop
		while (sc.hasNextLine()) {
			startTime = System.nanoTime();
			rbt.remove(new Node<Integer>(sc.nextInt()));
			endTime = System.nanoTime();
			totalTime += (endTime - startTime);
			count++;
		}
		
		//Print out the results
		System.out.println("\n***Deletion of " + count + " elements***");
		System.out.println("Total Time (ns): " + totalTime + "\nAverage Time (ns): " + totalTime / count);
		rt.gc();
		long memory = rt.totalMemory() - rt.freeMemory();
		System.out.println("Used memory in bytes: " + memory);
		System.out.println("Used memory in megabytes: " + bytesToMegabytes(memory));
	}
	
	/**
	 * Red Black search. A method that searches a certain amount of elements from
	 * the red black tree. The method then prints out the time it took to complete
	 * all of the operations.
	 *
	 * @param rbFile the file that contains all of the numbers to be searched
	 * @throws FileNotFoundException the file not found exception
	 */
	private static void rbSearch(File rbFile) throws FileNotFoundException {
		
		RedBlackTree<Integer> rbt = new RedBlackTree<Integer>();
		rbt = rbInsert(rbFile, false);
		int count = 0;
		
		//Create a scanner on the file
		Scanner sc = new Scanner(rbFile);
		
		//Initialize the timing variables
		long totalTime = 0, startTime = 0, endTime = 0;
		
		// Insert Loop with printing
		Runtime rt = Runtime.getRuntime();
		
		// Delete Loop
		while (sc.hasNextLine()) {
			startTime = System.nanoTime();
			rbt.search(sc.nextInt());
			endTime = System.nanoTime();
			totalTime += (endTime - startTime);
			count++;
		}
		
		//Print out the results
		System.out.println("\n***Search of " + count + " elements***");
		System.out.println("Total Time (ns): " + totalTime + "\nAverage Time (ns): " + totalTime / count);
		rt.gc();
		long memory = rt.totalMemory() - rt.freeMemory();
		System.out.println("Used memory in bytes: " + memory);
		System.out.println("Used memory in megabytes: " + bytesToMegabytes(memory));
		
	}

	/**
	 * Console UI. This contains the logic for the UI as well as
	 * the logic for retrieving the users answers to the responses.
	 * These answers are then used to display the correct information
	 * that the user is attempting to retrieve.
	 *
	 * @return the int[] with the users request
	 */
	private static int[] consoleUI() {
		//Set up the scanner for the users input
		Scanner in = new Scanner(System.in);
		
		//Set the default values of the users decision
		int[] userInput = { -1, -1 };
		
		//Print out initial message
		System.out.println("\nWelcome to the Red Black Tree test machine!\n");
		
		//First while loop for users decision
		while (true) {
			System.out.println("Please enter what function you would like to test.\n" + "1. Insertion\n"
					+ "2. Deletion\n" + "3. Search\n" + "4. Quit");
			
			//Checks if the user has input an int
			if (in.hasNextInt()) {
				
				//Sets the integer to the users first value
				userInput[0] = in.nextInt();
				
				//If this value is a 4, then the user wants to quit the program
				if (userInput[0] == 4)
					return userInput;
				
				//If the value is between here, the user wants to pick an amount
				else if (userInput[0] > 0 && userInput[0] < 4) {
					
					//Second while loop to get the amount the user would like to test
					while (true) {
						System.out.println("Please enter the amount of variables you would like to test.\n" 
											+ "1. 100\n"
											+ "2. 500\n"
											+ "3. 1000\n" 
											+ "4. 2500\n" 
											+ "5. 5000\n" 
											+ "6. 7500\n" 
											+ "7. 10000\n"
											+ "8. Quit\n" 
											+ "9. Back");
						
						//Checks if they input an integer or not
						if (in.hasNextInt()) {
							userInput[1] = in.nextInt();
							
							//Last check to see if the integer is valid or not
							if(userInput[1] > 0 && userInput[1] < 9)
								return userInput; // If the value is valid, we send the decisions to the main
							//If this is selected, the user wants to go back a loop
							else if(userInput[1] == 9)
								break;
							else
								System.out.println("\nINVALID INTEGER\n"); //The user input an invalid integer, and we must try to get 
						} else {
							System.out.println("\nINVALID VALUE\n"); //If the user input something other than an integer
							in.nextLine();
						}

					}
				}else { 
					System.out.println("\nINVALID INTEGER\n"); //The user input an invalid integer, and we must get back to the first while to ask again
				}
					

			} else {//If the user input something other than an integer
				System.out.println("\nINVALID VALUE\n"); 
				in.nextLine();
			}

		}
	}

}
