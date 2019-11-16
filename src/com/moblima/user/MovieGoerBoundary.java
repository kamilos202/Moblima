package com.moblima.user;

import java.io.IOException;
import java.util.ArrayList;

import com.moblima.booking.Booking;
import com.moblima.booking.BookingPage;
import com.moblima.movie.Movie;
import com.moblima.movie.MovieListing;
import com.moblima.rating.Rating;
import com.moblima.util.Sorting;
import com.moblima.util.UserInputs;

public class MovieGoerBoundary extends UserBoundary
{

	User userLogedIn;
	String format = "%-40s%s%n";
	public MovieGoerBoundary(MovieGoer movieGoer)
	{
		userLogedIn = movieGoer;
	}
	
	@Override
	public void performActions() throws IOException
	{
		System.out.println("these are the moviegoer actions");
		boolean loggedIn = true;
		while(loggedIn)
		{
			System.out.println("=========== MovieGoer module MENU ===========\n");
			System.out.println("What action do you want to perform: ");
			System.out.println("1: Rate a movie");
			System.out.println("2: Display available movies in all Cineplexes");
			System.out.println("3: Book a movie");
			System.out.println("4: Retrieve booking history");
			System.out.println("5: List movies ordered by sold tickets");
			System.out.println("6: List movies by ratings");
			System.out.println("8: logout");
			System.out.println("\n=============================================");


			
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
					BookingPage page = new BookingPage(userLogedIn.getUsername(),userLogedIn.getBirthdate());
					page.bookMovie();
					break;
				case 4:
					Booking.retrieveHistory(userLogedIn.getUsername());
					break;
				case 5:
					listTicketOrderMovies();
					break;
				case 6:
					listRatingOrderMovies();
					break;
				case 8:
					loggedIn = false;
					break;
				default:
					System.out.println("Please enter one of the valid options");
					break;
			}
		}
	}
	
	public void giveRating()
	{
		MovieListing.addRatingToMovie(userLogedIn);
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

					while(true){
						System.out.println("Do you want to see previous ratings? y/n");
						char ansR = UserInputs.gatValidCharInputForAnswer();

						if(ansR == 'y'){
							ArrayList<Rating> ratings = new ArrayList<Rating>();
							ratings = movies.get(movieChoice-1).getRatings();

							for(int i = 0;i<ratings.size();i++){
								System.out.println("\n\n"+ratings.get(i).getUser().getUsername()+"\n"+ratings.get(i).getDescription()+"\n\n");
							}
							break;
						}else{
							break;
						}
					}




				}else{
					break;
				}




			}
		}

	}

	public void listTicketOrderMovies(){

		ArrayList<Movie> movies = new ArrayList<>();
		movies = MovieListing.getMovies();
		Movie [] moviesArr = new Movie[movies.size()];

		for(int i=0;i<movies.size();i++){
			moviesArr[i] = movies.get(i);
		}

		moviesArr = Sorting.selectionSort(moviesArr);

		for(int i=0;i<moviesArr.length;i++){
			System.out.println(i+1+". "+moviesArr[i].getTitle());
		}
	}

	public void listRatingOrderMovies(){
		ArrayList<Movie> movies = new ArrayList<>();
		movies = MovieListing.getMovies();
		Movie [] moviesArr = new Movie[movies.size()];

		for(int i=0;i<movies.size();i++){
			moviesArr[i] = movies.get(i);
		}

		moviesArr = Sorting.selectionSort(moviesArr);

		for(int i=0;i<moviesArr.length;i++){
			System.out.println(i+1+". "+moviesArr[i].getTitle());
		}
	}

}
