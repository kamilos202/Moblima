package com.moblima.movie;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.moblima.database.DataBaseCommunication;
import com.moblima.rating.Rating;
import com.moblima.user.User;
import com.moblima.util.UserInputs;

public class MovieListing
{
    static ArrayList<Movie> movies= new ArrayList<Movie>();

    public static void createMovies(){
    	System.out.println("createMovies");
        //DataBaseCommunication data = new DataBaseCommunication();
        Map<Integer,List<String>> map = new HashMap<Integer,List<String>>();
        map = DataBaseCommunication.getMovies();

        //System.out.print(map);

        for(int i=0;i<map.size();i++)
        {
        	String[] showingInfo = map.get(i).get(6).split("/");
        	ArrayList<MovieShowing> showings = new ArrayList<MovieShowing>();
        	for(int j =0;j<showingInfo.length;j++)
        	{
				showings.add(new MovieShowing(showingInfo[j].split("\\|")));
        	}
            movies.add(new Movie(map.get(i).get(0), map.get(i).get(1), map.get(i).get(2), map.get(i).get(3), map.get(i).get(4),
					Double.parseDouble(map.get(i).get(5)),showings));
			System.out.print("thisssssssssssssssssssss"+map.get(i).get(0));
			// setting title for every showing
			for(int a=0;a<showingInfo.length;a++){
				showings.get(a).setMovie(movies.get(i));
			}
        }
        
		createWeeklyShowings();
		System.out.println("after create weeklyshowin:::::movie size" + movies.size());
    }

    
    @SuppressWarnings("deprecation")
	public static void createWeeklyShowings()
    {
    	for(int i =0;i<movies.size();i++)
    	{
    		ArrayList<MovieShowing> currentShowings = movies.get(i).getShowings();
			@SuppressWarnings("unchecked")
			ArrayList<MovieShowing>	allShowings = (ArrayList<MovieShowing>) currentShowings.clone(); 
    		for(int j=0;j<currentShowings.size();j++)
    		{
    			
    			if(movies.get(i).getShowings().get(j).isWeekly() && movies.get(i).getShowings().get(j).getCineplex().getForwardScheduling() >=7)
    			{
    				System.out.println("Create extra showings");
    				Date currentTime = new Date();
    				Date finalSchedule = new Date(currentTime.getTime() - 1000 * (currentTime.getHours()*3600+currentTime.getMinutes()*60+
    						currentTime.getSeconds()) + (long) ((movies.get(i).getShowings().get(j).getCineplex().getForwardScheduling()+1)*24*3600*1000));
    				Date nextDate = new Date(movies.get(i).getShowings().get(j).getDate().getTime() + (long) 7*24*3600*1000);
    				System.out.println(nextDate);
    				System.out.println(new Date(movies.get(i).getShowings().get(j).getDate().getTime()));
    				System.out.println(finalSchedule);
    				while(nextDate.before(finalSchedule))
    				{
    					allShowings.add(new MovieShowing(currentShowings.get(j).getCineplex(),currentShowings.get(j).getCinemaRoom(),nextDate,
    							currentShowings.get(j).isWeekly(),currentShowings.get(j).getScheduleDuration()));
    					Date dummy = new Date(nextDate.getTime() + (long) 7*24*3600*1000);
    					nextDate = dummy;
    				}
    			}
    			System.out.println(j);
    			movies.get(i).setShowings(allShowings);
    		}
    		
    	}
    }
    
    
    public void printMovieTitle(){
        int i = 1;
        for(Movie movie : movies){
            System.out.println(i+".\t"+movie.getTitle());
            i++;
        }
    }
    
    public static ArrayList<Movie> getMovies(){return movies;}
    
    public Movie getMovieByName(String name)
    {
    	for(int i =0;i<movies.size();i++)
    	{
    		if(movies.get(i).getTitle().contentEquals(name)) return movies.get(i);
    	}
    	return null;
    }
    
    public static void addRatingToMovie(User user)
    {
		System.out.println("Please enter the movie you want to give a rating for: ");
		System.out.println("Movie.size::::::::::"+movies.size());
		for(int i = 0;i<movies.size();i++)
		{
			System.out.println(i+1+": " + movies.get(i).getMovieName());
		}
		int choice = UserInputs.getValidIntegerInput()-1;
		if(choice<movies.size())
		{

			ArrayList<Rating> currentRatings = movies.get(choice).getRatings();
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
		    		movies.get(choice).addRating(user, score, description);
		    		System.out.println("Rating succesfully added to the system");
		    	}
				
			}
		}
		else System.out.println("Error: select a valid movie");
    	
    }
    



}
