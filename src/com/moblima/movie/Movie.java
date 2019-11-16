package com.moblima.movie;

import java.util.ArrayList;
import java.util.List;

import com.moblima.database.DataBase;
import com.moblima.rating.Rating;
import com.moblima.user.User;
import com.moblima.user.UserControl;

public class Movie 
{
    private String title;
    private String synopString;
    private String director;
    private String cast;
    private String status;
    private String movieType;
    private int duration;
    private ArrayList<MovieShowing> showings;
    private ArrayList<Rating> ratings = new ArrayList<Rating>();
    private double averageRating;
    private int ticketsSold;
    private static String ended = "End of Showing";

    /**
     * 
     * @param title
     * @param synopString
     * @param status
     * @param director
     * @param cast
     * @param duration
     * @param showings
     */
    public Movie(String title, String synopString, String director,String cast, String status, int duration,ArrayList<MovieShowing> showings,int sold){
    	
    	System.out.println("initiate movie");
        this.title = title;
        this.synopString = synopString;
        this.director = director;
        this.cast = cast;
        this.status = status;
        this.averageRating = 0.0;  //to be retrieved from rating.txt
        this.duration = duration;
        this.showings = showings;
        this.ticketsSold = sold;
        System.out.println("------------------------------------");
        MovieControl.retrieveRatingsFromDatabase(this);
    }
    /**
     * 
     */

    /**
     * 
     * @param user
     * @param score
     * @param description
     */
   
    /**
     * 
     */
    /**
     * 
     * Getters for Movie class
     */
    public String getMovieName(){
        return title;
    }
    public String getTitle(){
        return title;
    }
    public String getSynop(){
        return synopString;
    }
    public String getCast(){
        return cast;
    }
    public String getDirector(){
        return director;
    }
    public String getStatus(){
        return status;
    }
    public double getRating(){
        return MovieControl.getAverageRating(this);
    }
    public int getTicketsSold() {
    	return this.ticketsSold;
    }
    public int getDuration() {
    	return this.duration;
    }
    
    public void setTitle(String newTitle)
    {
    	this.title = newTitle;
    }
    
    public void setSynopsis(String newSynopsis)
    {
    	this.synopString = newSynopsis;
    }
    
    public void setDirector(String newDirector)
    {
    	this.director = newDirector;
    }
    
    public void setCast(String newCast)
    {
    	this.cast = newCast;
    }
    
    public void setStatus(String newStatus)
    {
    	this.status = newStatus;
    }
    
    public void setType(String newType)
    {
    	this.movieType = newType;
    }
    
    public void setDuration(int newDuration)
    {
    	this.duration = newDuration;
    }
    
    public void setSale(int newSold)
    {
    	this.ticketsSold = newSold;
    }
    
    public void setRating(double newRating)
    {
    	this.averageRating = newRating;
    }
    
    public boolean isEnded() {return this.status.equals(ended);}
    
    public ArrayList<Rating> getRatings() {return ratings;}
    public ArrayList<MovieShowing> getShowings() {return showings;}

    /**
     * Displaying movie ratings
     */
    /**
     * Set showing
     * @param newShowings
     */
    public void setShowings(ArrayList<MovieShowing> newShowings) {showings = newShowings;}
    
 
    
    

}
