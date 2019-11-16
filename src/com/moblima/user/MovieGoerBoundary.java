package com.moblima.user;

import java.io.IOException;
import java.util.ArrayList;

import com.moblima.booking.Booking;
import com.moblima.booking.BookingPage;
import com.moblima.database.DataBase;
import com.moblima.movie.Movie;
import com.moblima.movie.MovieControl;
import com.moblima.movie.MovieListing;
import com.moblima.rating.Rating;
import com.moblima.util.Sorting;
import com.moblima.util.UserInputs;

public class MovieGoerBoundary extends UserBoundary
{

	BookingPage page;

	User userLogedIn;
	public MovieGoerBoundary(MovieGoer movieGoer)
	{
		userLogedIn = movieGoer;
		page = new BookingPage(userLogedIn.getUsername(),userLogedIn.getBirthdate());
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
					page.listMovies();
					break;
				case 3:
					page.bookMovie();
					break;
				case 4:
					Booking.retrieveHistory(userLogedIn.getUsername());
					break;
				case 5:
					page.listTicketOrderMovies();
					break;
				case 6:
					page.listRatingOrderMovies();
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
		System.out.println("Please enter the movie you want to give a rating for: ");
		Movie movie = chooseMovieFromList();
		
		
		if(MovieControl.canUserRate(movie, userLogedIn))
		{
			System.out.println("What score would you give this movie on a scale from 1 to 5 (decimals allowed): ");
			double score = UserInputs.getValidDoubleInput();
			System.out.println("Do you have any further comments for your rating: ");
			String description = UserInputs.getValidLineInput();
			if(description.equals("")) description = " ";
	    	if(!((score>=1.0)&&(score<=5.0)))
	    	{
	    		System.out.println("Error Invalid number for rating, ratings should be between 1.0 and 5.0, action cancelled "+ score);
	    	}
	    	else
	    	{
	    		MovieControl.addRating(movie, userLogedIn, score, description);
	    		System.out.println("Rating succesfully added to the system");
	    	}
			
		}
	}



}
