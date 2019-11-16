package com.moblima.cinema;
import java.util.ArrayList;
import java.util.Date;
import com.moblima.database.DataBase;
import com.moblima.movie.Movie;
import com.moblima.movie.MovieShowing;
/**
 * CinemaRoom class Represents Cinema room (Room with screen) where actual movie is displayed.
 * Cinema room is the part of the Cineplex
 */
public class CinemaRoom {
    String cineplexName;
    String cinemaName;
    double basicPrice;
    double cinemaPrice;
    int[][] cinemaLayout;

    Boolean suite;

    /**
     * CinemaRoom constructor
     * @param cineplexName is the name of the Cinema (building)
     * @param cinemaName is the name of the Cinema room (ex: Screen1)
     * @param basicPrice is the price which is applicable to rooms which are not premium ones
     * @param suite is boolean value indicates either room is premium (true) or standard (false)
     */
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

    /**
     * 
     * @return skeleton of the layout 
     */
    public int[][] getLayout() {
        return cinemaLayout;
    }
    /**
     * @return price of the ticket which takes place in this cimena room
     */
    public double getCinemaPrice(){
        return cinemaPrice;
    }
    /**
     * 
     * @return name of the cinema
     */
    public String getCinemaName(){
        return cinemaName;
    }
    /**
     * 
     * @return the occupation of this particular Cinema room
     */
    public ArrayList<MovieShowing> getHallOccupation()
    {
    	ArrayList<Movie> movies = DataBase.moviesPlaying;
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
    /**
     * 
     * @return whether Cinema Room is premium or not
     */
    public boolean getPremium(){
        return suite;
    }

}