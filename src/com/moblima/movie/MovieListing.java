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
    
    public static void addRatingToMovie(User user)
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
				if(currentRatings.get(j).getUser().equals(user))
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
		    		DataBase.moviesPlaying.get(choice).addRating(user, score, description);
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
    
    public static void removeShowing(Movie movie)
    {
    	System.out.println("Which showing do you want to remove: ");
    	int count = 0;
    	for(int i = 0;i<movie.getShowings().size();i++)
    	{
    		if(!movie.getShowings().get(i).isCopy())
    		{
    			System.out.println(count+1+": "+movie.getShowings().get(count));
    			count++;
    		}
    		
    	}
    	int choice = UserInputs.getValidIntegerInput();
    	if(choice>0&&choice<=count)
    	{
    		ArrayList<MovieShowing> currentShowings = movie.getShowings();
    		currentShowings.remove(choice-1);
    		movie.setShowings(currentShowings);
    	}
    	else System.out.println("Invalid choice, no showing selected");
    }
    
    public static void removeMovie(Movie movie)
    {
    	String oldInfo = movie.toDataBaseString();
    	DataBase.moviesPlaying.remove(movie);
    	movie.setStatus("End of Showing");
    	for(int i =0;i<DataBase.fullMovieArchive.size();i++)
    	{
    		System.out.println(i+ ":  contains: " + DataBase.fullMovieArchive.get(i).toDataBaseString());
    		if(DataBase.fullMovieArchive.get(i).getTitle().equals(movie.getTitle()))
    		{
    			System.out.println("Updated");
    			DataBase.fullMovieArchive.get(i).setStatus("End of Showing");
    		}
    	}
    	DataBase.replaceInDataBase(oldInfo, movie.toDataBaseString(), "movies.txt");
    	//moviesPlaying.remove(movie);
    	//DataBaseCommunication.replaceInDataBase(oldInfo, movie.toDataBaseString(), path);
    }

    public static void editMovie(Movie movie) throws IOException, ParseException
    {
    	boolean editing = true;
    	String oldInfo = movie.toDataBaseString();
    	int index1 = DataBase.moviesPlaying.indexOf(movie);
    	int index2 = DataBase.fullMovieArchive.indexOf(movie);
    	System.out.println(index1+" and "+index2);
    	while(editing)
    	{
    		System.out.println("What movie information do you want to edit?");
    		System.out.println("1: Movie title");
    		System.out.println("2: synopsis");
    		System.out.println("3: director");
    		System.out.println("4: cast");
    		System.out.println("5: status");
    		System.out.println("6: movie type");
    		System.out.println("7: duration");
    		System.out.println("8: Add a movieShowing");
    		System.out.println("9: Remove a movieShowing");
    		System.out.println("0: Save changes");
    		int choice = UserInputs.getValidIntegerInput();
    		switch(choice)
    		{
    			case 1:
    				System.out.println("Please enter new movie title: ");
    				movie.setTitle(UserInputs.getValidLineInput());
    				break;
    			case 2:
    				System.out.println("Please enter new movie synopsis: ");
    				movie.setSynopsis(UserInputs.getValidLineInput());
    				break;
    			case 3:
    				System.out.println("Please enter new movie director: ");
    				movie.setDirector(UserInputs.getValidLineInput());
    				break;
    			case 4:
    				System.out.println("Please enter new movie cast: ");
    				movie.setCast(UserInputs.getValidLineInput());
    				break;
    			case 5:
    				System.out.println("Please enter new movie status: ");
    				movie.setStatus(UserInputs.getValidLineInput());
    				break;
    			case 6:
    				System.out.println("Please enter new movie type: ");
    				movie.setType(UserInputs.getValidLineInput());
    				break;
    			case 7:
    				System.out.println("Please enter new movie duration: ");
    				movie.setDuration(UserInputs.getValidIntegerInput());
    				break;
    			case 9:
    				removeShowing(movie);
    				break;
    			case 0:
    				editing = false;
    				break;
    			default:
    				System.out.println("Please enter a number between 0-9");
    				break;
    		}
    	}
    	
    	DataBase.moviesPlaying.set(index1, movie);
    	DataBase.fullMovieArchive.set(index2, movie);
    	DataBase.replaceInDataBase(oldInfo, movie.toDataBaseString(), "movies.txt");
    	
    }

}
