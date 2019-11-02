package com.moblima.util;

import java.util.Scanner;

//this class is made to regulate user inputs and make sure only one manual inputstream is active at the time
public class UserInputs 
{
	static Scanner sc = new Scanner(System.in);
	
	public static String getValidStringInput()
	{
		return sc.next();
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

	public static char gatValidCharInput()
	{
		char chr;
		while(true)
		{
			chr = sc.next().charAt(0);
			if(chr>='A' && chr<='J')
				break;
			System.out.println("Please enter a valid row");
		}
		return chr;
	}
	
	//Close the inputstream at the end of the program
	public static void closeScanner()
	{
		sc.close();
	}
}
