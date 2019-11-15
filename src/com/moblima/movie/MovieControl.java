package com.moblima.movie;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.moblima.cinema.CinemaRoom;
import com.moblima.cinema.Cineplex;
import com.moblima.database.DataBase;

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
		String[] possibleStatus = {"Coming Soon","Premiere","Now Showing","End of Showing","Custom"};
		return possibleStatus;
	}
	
	public static void addMovie(List<String> movieInfo)
	{
		Movie movie = getMovieFromString(movieInfo);
		DataBase.fullMovieArchive.add(movie);
		DataBase.moviesPlaying.add(movie);
		String[] movieString = {"\n"+movie.toDataBaseString()};
		try {
			DataBase.appendToDataBase(movieString, "movies.txt");
		} catch (IOException e) {
			System.out.println("Failed to save information to database, please try again later or consult with product supplier");
		}
	}
	
	public static void addShowing(Movie movie,Cineplex cineplex, CinemaRoom room, Date date, boolean isWeekly, int duration)
	{
		ArrayList<MovieShowing> currentShowings = movie.getShowings();
		String oldInfo = movie.toDataBaseString();
		try {
			currentShowings.add(new MovieShowing(cineplex,room,date,isWeekly,duration,false));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		movie.setShowings(currentShowings);
		System.out.println("new showing: " + currentShowings.get(currentShowings.size()-1).toString());
		DataBase.replaceInDataBase(oldInfo, movie.toDataBaseString(), "movies.txt");
		movie.setShowings(MovieControl.createWeeklyShowings(movie));
	}
	
	public static void removeMovie(Movie movie)
	{
		String oldInfo = movie.toDataBaseString();
    	DataBase.moviesPlaying.remove(movie);
    	movie.setStatus(ended);
    	DataBase.replaceInDataBase(oldInfo, movie.toDataBaseString(), "movies.txt");
    	
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
            	
            	showings = createWeeklyShowings(movie);
            	movie.setShowings(showings);
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
	
}
