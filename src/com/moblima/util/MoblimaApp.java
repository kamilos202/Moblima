package com.moblima.util;
import java.io.IOException;
import java.text.ParseException;
/**
 * 
 */

import com.moblima.user.User;	
/**
 * @author Kamil Florowski
 *
 */

//Main body of the program, contains the welcome screen and provides user with further options to navigate the program
public class MoblimaApp {

	/**
	 * @param args
	 * @throws ParseException 
	 * @throws NumberFormatException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws NumberFormatException, ParseException, IOException {
		
		welcome();
		boolean programIsRunning = true;
		
		//While the program is running user can continuously use the program functions, until he/she decides to quit the program
		while(programIsRunning)
		{
			System.out.println("What do you want to do?");
			System.out.println("1: login using an existing account");
			System.out.println("2: register a new account");
			System.out.println("3: Quit application");
			int moduleEntering = UserInputs.getValidIntegerInput();
			switch(moduleEntering)
			{
				//user wants to login
				case 1:
					User user = User.getUser(); //return either an admin or a moviegoer instance
					if(!(user==null)) user.performActions(); //automatically gives correct user capabilities based on the returned instance
					else System.out.println("Error during login, please try again"); //login failed
					break;
				//register a new user to the system
				case 2:
					User.registerUser(); 
					break;
				//Exit the program
				case 3:
					System.out.println("Goodbye!");
					System.exit(0);
					UserInputs.closeScanner(); //Closes the inputstream
					break;
				//User gives an invalid input
				default:
					System.out.println("Please enter one of the aformentioned options");
			}
				
			}
		}


		
	

	//Show a pretty welcome message
	public static void welcome() {
		System.out.println("                    .-'''-.                                                  ");
		System.out.println("                   \'   _    \\            .---.                               ");
		System.out.println(" __  __   ___    /   /` '.   \\ /|        |   |.--. __  __   ___");              
		System.out.println("|  |/  `.'   `. .   |     \\  ' ||        |   ||__||  |/  `.'   `.");            
		System.out.println("|   .-.  .-.   '|   '      |  '||        |   |.--.|   .-.  .-.   '");           
		System.out.println("|  |  |  |  |  |\\    \\     / / ||  __    |   ||  ||  |  |  |  |  |    __     ");
		System.out.println("|  |  |  |  |  | `.   ` ..' /  ||/'__ '. |   ||  ||  |  |  |  |  | .:--.'.   ");
		System.out.println("|  |  |  |  |  |    '-...-'`   |:/`  '. '|   ||  ||  |  |  |  |  |/ |   \\ |  ");
		System.out.println("|  |  |  |  |  |               ||     | ||   ||  ||  |  |  |  |  |`\" __ | |  ");
		System.out.println("|__|  |__|  |__|               ||\\    / '|   ||__||__|  |__|  |__| .'.''| |  ");
		System.out.println("                               |/\'..' / '---'                    / /   | |_ ");
		System.out.println("                               '  `'-'`                           \\ \\._,\\ '/ ");
		System.out.println("                                                                   `--'  `\"  ");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("==============MOvie Booking and LIsting Management Application================");
	
	}
}
