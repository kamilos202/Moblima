package com.moblima.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * this class is made to regulate user inputs and make sure only one manual inputstream is active at the time
 */
public class UserInputs 
{
	static Scanner sc = new Scanner(System.in);
	
	/**
	 * Only works if not seperated by spaces
	 * @return
	 */
	public static String getValidStringInput()
	{
		return sc.next();
	}
	
	/**
	 * works for full lines of text
	 * @return
	 */
	public static String getValidLineInput()
	{
		//The dummy string makes sure something is entered so no empty string is returned unless this is desired.
		String dummy = getValidStringInput();
		return dummy + sc.nextLine();
	}
	
	/**
	 * Verify that the number inputted is actually an integer to reduce chances of unintended program behaviour
	 * @return
	 */
	public static int getValidIntegerInput()
	{
		while(!sc.hasNextInt())
		{
			sc.next();
			System.out.println("Please enter a valid number");
		}
		return sc.nextInt();
	}
	
	public static boolean getValidBooleanInput()
	{
		System.out.println("1: yes");
		System.out.println("2: no");
		int choice = getValidIntegerInput(0,3);
		
		return choice == 1 ? true : false;
	}
	
	public static int getValidIntegerInput(int min, int max)
	{
		boolean inLimits = false;
		int output = 0;
		while(!inLimits)
		{
			while(!sc.hasNextInt())
			{
				sc.next();
				System.out.println("Please enter a valid number");
			}
			output = sc.nextInt();
			if(output>min&&output<max)
			{
				inLimits = true;
			}
			else System.out.println("Your input is not within the given limits");
			
		}
		
		return output;
	
	}
	
	/**
	 * Checks for valid char imput while choosing seat row
	 * @return
	 */
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
	/**
	 * Checks for valid imput for question yes or no
	 * @return
	 */
	public static char gatValidCharInputForAnswer()
	{
		char chr;
		while(true)
		{
			chr = sc.next().charAt(0);
			if(chr=='y' || chr=='n')
				break;
			System.out.println("Please enter a valid answer");
		}
		return chr;
	}
	/**
	 * Checks for valid user's imput
	 * @return valid double variable
	 */
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
	
	public static boolean isDateValid(String date)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
		formatter.setLenient(false);
		try
		{
			formatter.parse(date);
		}
		catch(ParseException e)
		{
			return false;
		}
		return true;
	}
	
	public static Date getValidDate(boolean getHours)
	{
		boolean isValid = false;
		Date date = null;
		while(!isValid)
		{
			System.out.println("Please enter the date at which the movie will be played (dd): ");
			int day = UserInputs.getValidIntegerInput();
			System.out.println("Please enter the date at which the movie will be played (mm): ");
			int month = UserInputs.getValidIntegerInput();
			System.out.println("Please enter the date at which the movie will be played (yyyy): ");
			int year = UserInputs.getValidIntegerInput();
			if(isDateValid(day+"/"+month+"/"+year))
			{
				isValid = true;
				if(getHours)
				{
					System.out.println("Please enter the hour (0-23): ");
					int hours = getValidIntegerInput(-1,24);
					System.out.println("Please enter the minutes (0-59");
					int minutes = getValidIntegerInput(-1,60);
					date = new Date(year-1900,month-1,day,hours,minutes);
				}
				else date = new Date(year-1900,month-1,day);
			}
			else System.out.println("The entered date does not exist, please enter a valid date");
		}
		
		return date;
	}
	
	/**
	 * Close the inputstream at the end of the program
	 */
	public static void closeScanner()
	{
		sc.close();
	}
}
