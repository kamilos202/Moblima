package com.moblima.movie;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.moblima.cinema.CinemaRoom;
import com.moblima.cinema.Cineplex;
import com.moblima.database.DataBase;
import com.moblima.rating.Rating;
import com.moblima.user.Admin;
import com.moblima.user.MovieGoerBoundary;
import com.moblima.user.User;
import com.moblima.util.UserInputs;

public class MovieListing{
    
    
    public void printMovieTitle(){
        int i = 1;
        for(Movie movie : DataBase.moviesPlaying){
            System.out.println(i+".\t"+movie.getTitle());
            i++;
        }
    }
    
    public static ArrayList<Movie> getMovies(){return DataBase.moviesPlaying;}
    
    public static Movie getMovieByName(String name)
    {
    	for(int i =0;i<DataBase.moviesPlaying.size();i++)
    	{
    		if(DataBase.moviesPlaying.get(i).getTitle().contentEquals(name)) return DataBase.moviesPlaying.get(i);
    	}
    	return null;
    }
    
    public static void addRatingToMovie(MovieGoerBoundary movieGoerBoundary)
    {
		System.out.println("Please enter the movie you want to give a rating for: ");
		System.out.println("Movie.size::::::::::"+DataBase.moviesPlaying.size());
		for(int i = 0;i<DataBase.moviesPlaying.size();i++)
		{
			System.out.println(i+1+": " + DataBase.moviesPlaying.get(i).getMovieName());
		}
		int choice = UserInputs.getValidIntegerInput()-1;
		if(choice<DataBase.moviesPlaying.size())
		{

			ArrayList<Rating> currentRatings = DataBase.moviesPlaying.get(choice).getRatings();
			System.out.println("currentRatingArr'''''''"+currentRatings.size());
			boolean canRate = true;
			for(int j = 0;j<currentRatings.size();j++)
			{
				System.out.println("currratings"+currentRatings.get(j).toString());
				if(currentRatings.get(j).getUser().equals(movieGoerBoundary))
				{
					System.out.println("Error: You have already rated this movie");
					canRate = false;
				}
			}
			if(canRate)
			{
				System.out.println("What score would you give this movie on a scale from 1 to 5 (decimals allowed): ");
				double score = UserInputs.getValidDoubleInput();
				System.out.println("Do you have any further comments for your rating: ");
				//String dummy = UserInputs.getValidStringInput();
				String description = UserInputs.getValidLineInput();
				//System.out.println("Dummy: " + dummy + " + description" + description);
				if(description.equals("")) description = " ";
		    	if(!((score>=1.0)&&(score<=5.0)))
		    	{
		    		System.out.println("Error Invalid number for rating, ratings should be between 1.0 and 5.0, action cancelled "+ score);
		    	}
		    	else
		    	{
		    		DataBase.moviesPlaying.get(choice).addRating(movieGoerBoundary, score, description);
		    		System.out.println("Rating succesfully added to the system");
		    	}
				
			}
		}
		else System.out.println("Error: select a valid movie");
    	
    }
    
    public static int getMovieId(String name)
    {
    	for(int i =0;i<DataBase.moviesPlaying.size();i++)
    	{
    		if(DataBase.moviesPlaying.get(i).getMovieName().equals(name)) return i;
    	}
    	return -1;
    }
    



}
