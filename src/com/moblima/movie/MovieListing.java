package com.moblima.movie;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.moblima.database.DataBaseCommunication;

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
					
			// setting title for every showing
			for(int a=0;a<showingInfo.length;a++){
				showings.get(a).setMovie(movies.get(i));
			}
        }
        
        createWeeklyShowings();
	}
	
    
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
    



}
