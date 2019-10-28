package com.moblima.util;
import	java.util.Scanner;
import com.moblima.database.DataBaseCommunication;
import com.moblima.movie.MovieListing;
import com.moblima.user.User;	
/**
 * @author
 *
 */
public class MoblimaApp {



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		welcome();
		MovieListing mov = new MovieListing();

		
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
		;}	
		else if (moduleEntering == 'g') {
			
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
		}else if (moduleEntering == 'r'){
			mov.createMovies();
			mov.printMovieTitle();
		}
		
		//sc.close();
		

		
	}

	/**
	 * 
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
