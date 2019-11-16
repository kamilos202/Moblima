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

/**
 * MovieControl connects the user to the program
 * Methods in this class will be called by the boundary of the program to manipulate Movie, MovieShowing and Rating objects
 * @author Ivo Janssen
 *
 */
public class MovieControl 
{
	
	/**
	 * Checks whether a title is viable for a movie.
	 * This is done by checking if the entered title is already in use by any of the other movies in the archive
	 * @param newTitle Title which needs to be checked
	 * @return boolean true: Title can safely be used, false: title is already in use by a different movie
	 */
	public static boolean isNewTitleValid(String newTitle)
	{
		for(int i =0;i<DataBase.fullMovieArchive.size();i++)
		{
			if(DataBase.fullMovieArchive.get(i).getTitle().equals(newTitle)) return false;
		}
		return true;
	}
	
	/**
	 * Get all valid statusses for a movie
	 * @return String array of valid movie statusses
	 */
	public static String[] getValidMovieStatus()
	{
		String[] possibleStatus = {"Coming Soon","Premiere","Now Showing","End Of Showing","Custom"};
		return possibleStatus;
	}
	
	/**
	 * Adds a movie to the program. This incorporates adding it in real-time, adding it to the archive and saving in the database.
	 * This way the movie can instantly be used in the program without need for rebooting, but will also be saved to database immidiately
	 * to make sure it will be retrieved on next boot up, even when program is exited unexpected.
	 * Please note that not using the exact inputs as stated below will result in unintended behaviour. In order to correctly generate the inputs
	 * for this method use can be made of AdminBoundary.addMovie()
	 * @param movieInfo A string-wise description of the movie object in specific order: entry 0 contains the title of the movie. Entry 1 contains
	 * the synopsis of the movie. Entry 2 contains the status of the movie. Entry 3 contains the director of the movie. Entry 4 contains the cast
	 * of the movie. Entry 5 contains the duration of the movie in minutes. Entry 6 contains the amount of tickets sold for a movie, for a new
	 * movie initiate this field as 0. Entry 6 contains the showings of the movie, for a new movie these will be created independently and
	 * therefore this field should be entered as an empty string.
	 * 
	 */
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
	
	/**
	 * Create a showing object, based on its composition of individual data
	 * All data can easily be inputted by using the AdminBoundary.addShowing() method.
	 * @param movie The movie object to which the showing belongs
	 * @param cineplex The cineplex in which the movie will be played.
	 * @param room The specific room within the given cineplex in which the movie will be played
	 * @param date The date and time, given as a Java.Util.Date object at which the movie will be played
	 * @param isWeekly Refers to if this showing will be scheduled weekly, initiating as true will result in automatically creating showings
	 * every week at the same time for this movie until the final date specified by current date + forwardScheduling property of the given 
	 * cineplex
	 * @param duration The total duration the room should be reserved for: this includes advertisements, movie playtime and breaks
	 */
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
		DataBase.replaceInDataBase(oldInfo, toDateBaseString(movie), "movies.txt");
		movie.setShowings(MovieControl.createWeeklyShowings(movie));
	}
	
	/**
	 * Remove a movie from the list of movies which are played at the moment.
	 * The movie will not be removed from the movie archive, but it's status will automatically be updated to End Of Showing
	 * Database will be updated such that on fresh bootup the movie will still exist in the movie archive, but will no longer be visible in the
	 * list of current movies playing.
	 * @param movie The movie object to be removed from the list of movies which are currently playing
	 */
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
	
	/**
	 * Remove 1 particular showing of a movie. If this showing is initiated with the isWeekly parameter as true, then also all showings
	 * created based on this one will no longer be scheduled in starting from the next bootup of the program. Please note however that these
	 * other showings are still present in the program and will only disappear on reboot of the proram
	 * The movie and showings can easily be selected using the AdminBoundary.removeShowing() method.
	 * @param movie The movie the showing belongs to
	 * @param showing The showing to be removed
	 */
	public static void removeShowing(Movie movie, MovieShowing showing)
	{
		movie.getShowings().remove(showing);
	}
	
	
	/**
	 * Used to create a movie object based on its String-wise definition.
	 * Please not that the inputted parameters are really specific and wrong inputs might lead to unintended behaviour.
	 * @param movieInfo A string-wise description of the movie object in specific order: entry 0 contains the title of the movie. Entry 1 contains
	 * the synopsis of the movie. Entry 2 contains the status of the movie. Entry 3 contains the director of the movie. Entry 4 contains the cast
	 * of the movie. Entry 5 contains the duration of the movie in minutes. Entry 6 contains the amount of tickets sold for a movie, for a new
	 * movie initiate this field as 0. Entry 6 contains the showings of the movie, for a new movie these will be created independently and
	 * therefore this field should be entered as an empty string, however when showings are inputted using the correct format as described by 
	 * the database structure showings will automatically be added to the movieobject.
	 * @return The movie object defined by the String-wise composition
	 */
	public static Movie getMovieFromString(List<String> movieInfo)
	{
        
        	String[] showingInfo = movieInfo.get(7).split("/");
        	boolean hasShowings = !showingInfo[0].equals("");
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
            	showings = createWeeklyShowings(movie);
            	movie.setShowings(showings);
            	
            }
            
            
            return movie;
				
	}
	
	/**
	 * Create weekly showings for all showings with the characteristic isWeekly = true.
	 * @param movie The movie object to create weekly showings for
	 * @return allShowings: ArrayList of MovieShowing which includes the MovieShowing which will be shown on weekly basis
	 */
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
	
	/**
	 * Defines if a user can rate a movie. Every user can rate every movie only once
	 * @param movie movie to check if the user can rate it
	 * @param user the user who tries to give a rating 
	 * @return
	 */
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
	
	/**
	 * Add a rating to a movie.
	 * Inputs can be generated using MovieGoerBoundary.giveRating()
	 * @param movie Movie to be rated
	 * @param user User who gives the rating
	 * @param score	Rating given by the user, this is a double between 1.0 and 5.0
	 * @param description An additional description of the rating specified by the user
	 */
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
	    	Rating rating = new Rating(user,score,description);
	    	movie.getRatings().add(rating);
	    	
	    	String newRatingInfo = oldRatingInfo+";"+rating.toString();
	    	DataBase.replaceInDataBase(oldRatingInfo, newRatingInfo.replaceAll(";;", ";"), "ratings.txt");
	    	
	    	getAverageRating(movie);
	    }
	 
	 /**
	  * Calculates the average rating of a movie based on the ratings the users have given
	  * @param movie the movie to calculate the rating for
	  * @return average rating of all users, given as a double between 1.0 and 5.0, if no ratings are present 0.0 will be returned
	  */
	 public static double getAverageRating(Movie movie)
	    {
	    	double averageRating = 0;
	    	for(int i = 0;i<movie.getRatings().size();i++)
	    	{
	    		averageRating += movie.getRatings().get(i).getScore();
	    	}
	    	averageRating = Math.round(100*(averageRating / ((double)movie.getRatings().size())))/100.;
	    	return averageRating;
	    }
	 
	 /**
	  * Used during initialization of the program after reboot to load all ratings from the database into the program
	  * @param movie to retrieve the ratings for
	  */
	    public static void retrieveRatingsFromDatabase(Movie movie)
	    {
	    	List<String> moviesWithRating = DataBase.readFile("ratings.txt");
	    	String[] ratingString = null;
	    	for(int i =0;i<moviesWithRating.size();i++)
	    	{
	    		if(moviesWithRating.get(i).split(";")[0].equals(movie.getTitle()))
	    		{
	    			ratingString = moviesWithRating.get(i).substring(moviesWithRating.get(i).split(";")[0].length()+1).split(";");
	    			break;
	    		}
	    	}
	    	if(!(ratingString == null))
	    	{
	    		for(int j=0;j<ratingString.length;j++)
	        	{
	        		String[] ratingInfo = ratingString[j].split("\\|");

	                
	                if(!ratingInfo[0].equals(""))
	        		{
	        			movie.getRatings().add(new Rating(UserControl.getUserByName(ratingInfo[0]),Double.parseDouble(ratingInfo[1]),ratingInfo[2]));
	        		}
	        	}
	        	
	        	getAverageRating(movie);
	    	}
	    	
	    }
	    
	    /**
	     * Converts a movie object to a string which is used to save information to the database
	     * @param movie to convert to stringwise description
	     * @return A string containing all information of the move object to be saved to database
	     */
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
