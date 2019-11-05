package com.moblima.util;

import java.util.Scanner;

//this class is made to regulate user inputs and make sure only one manual inputstream is active at the time
public class UserInputs 
{
	static Scanner sc = new Scanner(System.in);
	
	//Only works if not seperated by spaces
	public static String getValidStringInput()
	{
		return sc.next();
	}
	
	//works for full lines of text
	public static String getValidLineInput()
	{
		//The dummy string makes sure something is entered so no empty string is returned unless this is desired.
		String dummy = getValidStringInput();
		return dummy + sc.nextLine();
	}
	
	//Verify that the number inputted is actually an integer to reduce chances of unintended program behaviour
	public static int getValidIntegerInput()
	{
		while(!sc.hasNextInt())
		{
			sc.next();
			System.out.println("Please enter a valid number");
		}
		return sc.nextInt();
	}
	
	public static double getValidDoubleInput()
	{
		while(!sc.hasNextDouble())
		{
			String input = sc.next();
			try {
				return Double.parseDouble(input);
			}
			catch(Exception e)
			{
				System.out.println("Please enter a valid decimal number");
			}
		}
		return sc.nextDouble();
	}
	
	
	//Close the inputstream at the end of the program
	public static void closeScanner()
	{
		sc.close();
	}
}
