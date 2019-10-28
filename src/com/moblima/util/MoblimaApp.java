package com.moblima.util;
import	java.util.Scanner;

import com.moblima.booking.BookingPage;
import com.moblima.database.DataBaseCommunication;
import com.moblima.movie.MovieListing;
import java.io.IOException;
import java.text.ParseException;
/**
 * 
 */

import com.moblima.user.User;	
/**
 * @author
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
		MovieListing mov = new MovieListing();
		Boolean programIsRunning = true;

		/*
		Scanner sc = new Scanner(System.in);
		System.out.println(DataBaseCommunication.readFile("movies.txt"));
		System.out.println("Are you a movie-goer or part of a staff?");
		System.out.print("Select respectively g/s:");	
		System.out.print("Select r to see movies:");	

		char moduleEntering = sc.next().charAt(0);
		if(moduleEntering == 's') {
			/*
			1. Login
			2. Create/Update/Remove movie listing
			3. Create/Update/Remove cinema show times and the movies to be shown
			4. Configure system settings
			*/
		//;}	
		//else if (moduleEntering == 'g') {
			
			//MovieGoerModule movieGoer = new MovieGoerModule();
			//movieGoer.listMovies();
				/*
				1. Search/List movie
				2. View movie details –including reviews and ratings
				3. Check seatavailabilityand selection of seat/s.
				4. Book and purchase ticket
				5. View booking history
				6. List the Top 
				5. ranking by ticket sales OR byoverall reviewers’ratings
				*/
				/*
		}else if (moduleEntering == 'r'){
			mov.createMovies();
			mov.printMovieTitle();
		boolean programIsRunning = true;
		*/
		
		//While the program is running user can continuously use the program functions, until he/she decides to quit the program
		while(programIsRunning)
		{
			System.out.println("What do you want to do?");
			System.out.println("1: login using an existing account");
			System.out.println("2: register a new account");
			System.out.println("3: Quit application");
			System.out.println("4: Show movies");
			System.out.println("5: Enter booking page");

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
				case 4:
					MovieListing list = new MovieListing();
					list.createMovies();
					list.printMovieTitle();	
					break;
				case 5:
					BookingPage page = new BookingPage();
					page.initiateCinemas();
					page.bookMovie();
					break;
				//User gives an invalid input
				default:
					System.out.println("Please enter one of the aformentioned options");
			}
				
			}
		
		}


	/**
	 * Show a pretty welcome message
	 */
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
