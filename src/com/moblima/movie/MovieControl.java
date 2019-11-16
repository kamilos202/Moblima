package com.moblima.movie;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.moblima.cinema.CinemaRoom;
import com.moblima.cinema.Cineplex;
import com.moblima.database.DataBase;
import com.moblima.rating.Rating;
import com.moblima.user.User;
import com.moblima.user.UserControl;

public class MovieControl 
{
	
	private static String ended = "End of Showing";
	
	public static boolean isNewTitleValid(String newTitle)
	{
		for(int i =0;i<DataBase.fullMovieArchive.size();i++)
		{
			if(DataBase.fullMovieArchive.get(i).getTitle().equals(newTitle)) return false;
		}
		return true;
	}
	
	public static String[] getValidMovieStatus()
	{
		String[] possibleStatus = {"Coming Soon","Premiere","Now Showing","End Of Showing","Custom"};
		return possibleStatus;
	}
	
	public static void addMovie(List<String> movieInfo)
	{
		Movie movie = getMovieFromString(movieInfo);
		DataBase.fullMovieArchive.add(movie);
		DataBase.moviesPlaying.add(movie);
		String[] movieString = {"\n"+toDateBaseString(movie)};
		try {
			DataBase.appendToDataBase(movieString, "movies.txt");
		} catch (IOException e) {
			System.out.println("Failed to save information to database, please try again later or consult with product supplier");
		}
	}
	
	public static void addShowing(Movie movie,Cineplex cineplex, CinemaRoom room, Date date, boolean isWeekly, int duration)
	{
		ArrayList<MovieShowing> currentShowings = movie.getShowings();
		String oldInfo = toDateBaseString(movie);
		try {
			currentShowings.add(new MovieShowing(cineplex,room,date,isWeekly,duration,false));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		movie.setShowings(currentShowings);
		System.out.println("new showing: " + currentShowings.get(currentShowings.size()-1).toString());
		DataBase.replaceInDataBase(oldInfo, toDateBaseString(movie), "movies.txt");
		movie.setShowings(MovieControl.createWeeklyShowings(movie));
	}
	
	public static void removeMovie(Movie movie)
	{
		String oldInfo = toDateBaseString(movie);
		DataBase.moviesPlaying.remove(movie);
		String[] possibleStatus = getValidMovieStatus();
    	movie.setStatus(possibleStatus[possibleStatus.length-2]);
    	DataBase.replaceInDataBase(oldInfo, toDateBaseString(movie), "movies.txt");
    	
    	for(int i = 0;i<DataBase.fullMovieArchive.size();i++)
    	{
    		if(movie.getTitle().equals(DataBase.fullMovieArchive.get(i).getTitle())) System.out.println("the status in fullmoviearchive: " + 
    	DataBase.fullMovieArchive.get(i).getStatus());
    	}
    	for(int i = 0;i<DataBase.moviesPlaying.size();i++)
    	{
    		if(movie.getTitle().equals(DataBase.moviesPlaying.get(i).getTitle())) System.out.println("the status in moviesPlaying: " + 
    	DataBase.moviesPlaying.get(i).getStatus());
    	}
    	
	}
	
	public static void removeShowing(Movie movie, MovieShowing showing)
	{
		movie.getShowings().remove(showing);
	}
	
	
	
	public static Movie getMovieFromString(List<String> movieInfo)
	{
		System.out.println("createMovies");
		System.out.println("We are creating new movies: " + movieInfo.size());
		for(int i =0;i<movieInfo.size();i++)
		{
			System.out.println(movieInfo.get(i));
		}
        //DataBaseCommunication data = new DataBaseCommunication();
        
        	String[] showingInfo = movieInfo.get(7).split("/");
        	boolean hasShowings = !showingInfo[0].equals("");
        	System.out.println("SHOWINGINFO: "+showingInfo[0]);
        	ArrayList<MovieShowing> showings = new ArrayList<MovieShowing>();
        	
        	Movie movie = new Movie(movieInfo.get(0), movieInfo.get(1),movieInfo.get(3),
        			movieInfo.get(4), movieInfo.get(2),Integer.parseInt(movieInfo.get(5)),showings,Integer.parseInt(movieInfo.get(6)));
            if(hasShowings)
            {
            	for(int j =0;j<showingInfo.length;j++)
            	{
            		if(hasShowings)
						try {
							showings.add(new MovieShowing(showingInfo[j].split("\\|")));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
            		showings.get(j).setMovie(movie);
            	}
            	System.out.println("Showings of the movie before creating weekly showings:");
            	for(int k = 0;k<movie.getShowings().size();k++)
            	{
            		System.out.println(movie.getShowings().get(k).toString());
            	}
            	showings = createWeeklyShowings(movie);
            	movie.setShowings(showings);
            	
            	System.out.println("Showing of the movie after creating weekly showings: ");
            	for(int l = 0; l<movie.getShowings().size();l++)
            	{
            		System.out.println(movie.getShowings().get(l).toString());
            	}
            }
            
            
            return movie;
				
	}
	
	public static ArrayList<MovieShowing> createWeeklyShowings(Movie movie)
	{
		ArrayList<MovieShowing> currentShowings = movie.getShowings();
		@SuppressWarnings("unchecked")
		ArrayList<MovieShowing>	allShowings = (ArrayList<MovieShowing>) currentShowings.clone(); 
		for(int j=0;j<currentShowings.size();j++)
		{
			if((!currentShowings.get(j).isCopy())&&(!currentShowings.get(j).hasCreatedWeeklyShowings()))
			{
				if(movie.getShowings().get(j).isWeekly() && movie.getShowings().get(j).getCineplex().getForwardScheduling() >=7)
    			{
    				Date currentTime = new Date();
    				Date finalSchedule = new Date(currentTime.getTime() - 1000 * (currentTime.getHours()*3600+currentTime.getMinutes()*60+
    						currentTime.getSeconds()) + (long) ((movie.getShowings().get(j).getCineplex().getForwardScheduling()+1)*24*3600*1000));
    				Date nextDate = new Date(movie.getShowings().get(j).getDate().getTime() + (long) 7*24*3600*1000);
    				
    				while(nextDate.before(finalSchedule))
    				{
    					try {
							allShowings.add(new MovieShowing(currentShowings.get(j).getCineplex(),currentShowings.get(j).getCinemaRoom(),nextDate,
									currentShowings.get(j).isWeekly(),currentShowings.get(j).getScheduleDuration(),true));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
    					Date dummy = new Date(nextDate.getTime() + (long) 7*24*3600*1000);
    					nextDate = dummy;
    				}
    			}
    			movie.getShowings().get(j).weeklyShowingsInitiated(true);
			}
			
		}
		
		return allShowings;
	}
	
	public static boolean canUserRate(Movie movie, User user)
	{
		for(int i = 0;i<movie.getRatings().size();i++)
		{
			if(movie.getRatings().get(i).getUser().equals(user)) 
			{
				System.out.println("Error: You have already rated this movie, action cancelled");
				return false;
			}
		}
		return true;
	}
	
	 public static void addRating(Movie movie, User user, double score, String description)
	    {

	    	String oldRatingInfo = ""+movie.getTitle()+";";
	    	for(int i = 0;i<movie.getRatings().size();i++)
	    	{
	    		oldRatingInfo += movie.getRatings().get(i).toString();
	    		if(i!=movie.getRatings().size()-1)
	    		{
	    			oldRatingInfo += ";";
	    		}
	    	}
	    	System.out.println(oldRatingInfo);
	    	Rating rating = new Rating(user,score,description);
	    	movie.getRatings().add(rating);
	    	
	    	String newRatingInfo = oldRatingInfo+";"+rating.toString();
	    	DataBase.replaceInDataBase(oldRatingInfo, newRatingInfo.replaceAll(";;", ";"), "ratings.txt");
	    	
	    	getAverageRating(movie);
	    }
	 
	 public static double getAverageRating(Movie movie)
	    {
	    	double averageRating = 0;
	    	for(int i = 0;i<movie.getRatings().size();i++)
	    	{
	    		System.out.println("calculate average: " + movie.getRatings().get(i).getScore());
	    		averageRating += movie.getRatings().get(i).getScore();
	    		System.out.println(movie.getRatings().get(i).getDescription());
	    	}
	    	averageRating = Math.round(100*(averageRating / ((double)movie.getRatings().size())))/100.;
	    	System.out.println("Calculated new averageRating: " + averageRating);
	    	return averageRating;
	    }
	 
	    public static void retrieveRatingsFromDatabase(Movie movie)
	    {
	    	System.out.println("Title " + movie.getTitle());
	    	List<String> moviesWithRating = DataBase.readFile("ratings.txt");
	    	String[] ratingString = null;
	    	System.out.println("Ratings:" + moviesWithRating.get(0));
	    	for(int i =0;i<moviesWithRating.size();i++)
	    	{
	    		System.out.println(" We are looking for this"+moviesWithRating.get(i).split(";")[0]);
	    		if(moviesWithRating.get(i).split(";")[0].equals(movie.getTitle()))
	    		{
	    			ratingString = moviesWithRating.get(i).substring(moviesWithRating.get(i).split(";")[0].length()+1).split(";");
	    			break;
	    		}
	    	}
	    	System.out.println("ratingStringIfNull:::::"+ratingString==null);
	    	if(!(ratingString == null))
	    	{
	            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
	    		for(int j=0;j<ratingString.length;j++)
	        	{
	        		String[] ratingInfo = ratingString[j].split("\\|");
	        		System.out.println("ratingString " + ratingString[j]);
	                System.out.println("ratingInfo: " + ratingInfo[0]);

	                
	                if(!ratingInfo[0].equals(""))
	        		{
	        			movie.getRatings().add(new Rating(UserControl.getUserByName(ratingInfo[0]),Double.parseDouble(ratingInfo[1]),ratingInfo[2]));
	        		}
	        	}
	        	
	        	getAverageRating(movie);
	    	}
	    	
	    }
	    
	    public static String toDateBaseString(Movie movie)
	    {
	    	String result = "TITLE:"+movie.getTitle()+";SYNOPSIS:"+movie.getSynop()+";STATUS:"+movie.getStatus()+
	    			";DIRECTOR:"+movie.getDirector()+";CAST:"+movie.getCast()+";DURATION:"+
	    				movie.getDuration()+";TICKETS:"+movie.getTicketsSold() + ";SHOWINGS:";
	    	
	    	for(int i =0;i<movie.getShowings().size();i++)
	    	{
	    		if(!movie.getShowings().get(i).isCopy())
	    		{
	    			result = result + movie.getShowings().get(i).toString() + "/";
	    		}
	    	}
	    	result = (String) result.subSequence(0, result.length()-1) + ";";
	    	//System.out.println("result: " + result);
	    	//System.out.println("result: " + DataBaseCommunication.readFile("movies.txt").get(0));
	    	return result;
	    }
	
}
