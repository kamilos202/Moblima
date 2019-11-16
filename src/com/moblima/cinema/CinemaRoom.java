package com.moblima.cinema;
import java.util.ArrayList;
import java.util.Date;
import com.moblima.movie.Movie;
import com.moblima.movie.MovieListing;
import com.moblima.movie.MovieShowing;

public class CinemaRoom {
    String cineplexName;
    String cinemaName;
    double basicPrice;
    double cinemaPrice;
    int[][] cinemaLayout;

    Boolean suite;

    public CinemaRoom(String cineplexName, String cinemaName, double basicPrice, Boolean suite) {
        this.cineplexName = cineplexName;
        this.cinemaName = cinemaName;
        this.suite = false;
        cinemaPrice = basicPrice;
        if (suite == true) {
            this.suite = true;
            cinemaPrice += 7;
            cinemaLayout = Cineplex.suiteLayout();
        } else {
            cinemaLayout = Cineplex.standardLayout();
        }
    }

    public int[][] getLayout() {
        return cinemaLayout;
    }

    public double getCinemaPrice(){
        return cinemaPrice;
    }
    public String getCinemaName(){
        return cinemaName;
    }
    
    public ArrayList<MovieShowing> getHallOccupation()
    {
    	ArrayList<Movie> movies = MovieListing.getMovies();
    	ArrayList<MovieShowing> showings = new ArrayList<MovieShowing>();
    	for(int i =0;i<movies.size();i++)
    	{
    		for(int j =0;j<movies.get(i).getShowings().size();j++)
    		{
    			if(this.equals(movies.get(i).getShowings().get(j).getCinemaRoom())&&new Date().before(movies.get(i).getShowings().get(j).getDate())) 
    				showings.add(movies.get(i).getShowings().get(j));
    		}
    	}
    	return showings;
    }
    public boolean getPremium(){
        return suite;
    }

}