package com.moblima.user;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.moblima.booking.Booking;
import com.moblima.booking.BookingPage;
import com.moblima.movie.Movie;
import com.moblima.movie.MovieListing;
import com.moblima.util.UserInputs;

/**
 * 
 */
public class MovieGoer extends User {

	String username, password;
	Date birthdate;
	int id;
	String format = "%-40s%s%n";
	MovieGoer current;

	/**
	 * 
	 * @param username
	 * @param password
	 * @param id
	 * @param birthdate
	 * @throws ParseException
	 */
	public MovieGoer(String username, String password, int id, String birthdate) throws ParseException {
		this.username = username;
		this.password = password;
		this.id = id;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
		this.birthdate = formatter.parse(birthdate);

		System.out.println("I am a moviegoer");
	}

	/**
	 * 
	 */
	@Override
	public String getUsername() {
		return username;
	}

	/**
	 * @throws IOException
	 * 
	 */
	@Override
	public void performActions() throws IOException
	{
		System.out.println("these are the moviegoer actions");
		boolean loggedIn = true;
		while(loggedIn)
		{
			System.out.println("What action do you want to perform: ");
			System.out.println("1: Rate a movie");
			System.out.println("2: Display available movies in all Cineplexes");
			System.out.println("3: Book a movie");
			System.out.println("4: Retrieve booking history");


			System.out.println("8: logout");
			
			int actionChoice = UserInputs.getValidIntegerInput();
			switch(actionChoice)
			{
				case 1:
					giveRating();
					break;
				case 2:
					listMovies();
					break;
				case 3:
					BookingPage page = new BookingPage(username,birthdate);
					page.bookMovie();
					break;
				case 4:
					Booking.retrieveHistory(username);
				case 8:
					loggedIn = false;
					break;
				default:
					System.out.println("Please enter one of the valid options");
					break;
			}
		}
	}
	
	/**
	 * 
	 */
	public void giveRating()
	{
		MovieListing.addRatingToMovie(this);
	}

	/**
	 * Shows movies and all information about them.
	 */
	public void listMovies(){
		ArrayList<Movie> movies = new ArrayList<>();
		movies = MovieListing.getMovies();

		for(int i = 0; i<movies.size() ; i++){
			System.out.println((i+1)+". "+movies.get(i).getTitle());
		}
		System.out.println("Do you want to find out more about any movie? (y/n)");
		char ans = UserInputs.gatValidCharInputForAnswer();
		
		if(ans == 'y'){
			while(true){
				System.out.println("Type the number of the movie you want to get more information about\nor type any other number to get back to previous menu:");

				int movieChoice = UserInputs.getValidIntegerInput();

				if(movieChoice>=1 && movieChoice<=movies.size()){

					System.out.printf(format,"Title: ",movies.get(movieChoice-1).getTitle());
					System.out.printf(format,"Director: ",movies.get(movieChoice-1).getDirector());
					System.out.printf(format,"Cast: ",movies.get(movieChoice-1).getCast());
					System.out.printf(format,"Description: ",movies.get(movieChoice-1).getSynop());
					System.out.printf(format,"Rating: ",movies.get(movieChoice-1).getRating());
					System.out.printf(format,"Status: ",movies.get(movieChoice-1).getStatus());
					System.out.println("\n");



				}else{
					break;
				}




			}
		}

	}

}
