package com.moblima.movie;

import java.util.ArrayList;
import com.moblima.rating.Rating;
/**
 * Movie is an entity class.
 * The class contains all the information related to an individual movie.
 * The class contains methods to get and set the data of the movie object
 * @author Ivo Janssen
 *
 */
public class Movie 
{
    private String title;
    private String synopString;
    private String director;
    private String cast;
    private String status;
    @SuppressWarnings("unused")
	private String movieType;
    private int duration;
    private ArrayList<MovieShowing> showings;
    private ArrayList<Rating> ratings = new ArrayList<Rating>();
    @SuppressWarnings("unused")
	private double averageRating;
    private int ticketsSold;

    /**
     * Used to initiate a movie object
     * @param title The title of the movie
     * @param synopString The Synopsis of the movie
     * @param status The status of the movie
     * @param director The director(s) of the movie
     * @param cast The cast of the movie
     * @param duration The duration of the movie in minutes
     * @param showings An ArrayList of MovieShowing Objects, containing all showings of this movie
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
     * Get the title of the movie
     * @return title
     */
    public String getTitle(){
        return title;
    }
    
    /**
     * Get the synopsis of the movie
     * @return synopsis
     */
    public String getSynop(){
        return synopString;
    }
    
    /**
     * Get the cast of the movie
     * @return cast
     */
    public String getCast(){
        return cast;
    }
    
    /**
     * Get the director of the movie
     * @return director
     */
    public String getDirector(){
        return director;
    }
    
    /**
     * Get the status of the movie
     * @return status
     */
    public String getStatus(){
        return status;
    }
    
    /**
     * Get and update the average rating of the movie as defined by the ratings given by all the users
     * @return average rating
     */
    public double getRating(){
        return MovieControl.getAverageRating(this);
    }
    
    /**
     * Get the amount of tickets sold for this movies
     * @return amount of tickets sold
     */
    public int getTicketsSold() {
    	return this.ticketsSold;
    }
    
    /**
     * Get the duration of the movie in minutes
     * This is the duration of the movie only, extra time for breaks and advertisements is not incorparated here, but will be accounted for in the movieshowing
     * @return duration
     */
    public int getDuration() {
    	return this.duration;
    }
    
    /**
     * Change the title of the movie
     * @param newTitle the new title of the movie
     */
    public void setTitle(String newTitle)
    {
    	this.title = newTitle;
    }
    
    /**
     * Change the synopsis of the movie
     * @param newSynopsis the new synopsis of the movie
     */
    public void setSynopsis(String newSynopsis)
    {
    	this.synopString = newSynopsis;
    }
    
    /**
     * Change the director(s) of the movie
     * @param newDirector String containing the director(s)
     */
    public void setDirector(String newDirector)
    {
    	this.director = newDirector;
    }
    
    /**
     * Change the cast of the movie
     * @param newCast String containing the new cast of the movie
     */
    public void setCast(String newCast)
    {
    	this.cast = newCast;
    }
    
    /**
     * Change the status of the movie.
     * Note that the possible statuses are defined by MovieControl.getValidMovieStatus()
     * @param newStatus the new status of the movie
     */
    public void setStatus(String newStatus)
    {
    	this.status = newStatus;
    }
    
    /**
     * Change the genre of the movie
     * @param newType the new genre of the movie
     */
    public void setType(String newType)
    {
    	this.movieType = newType;
    }
    
    /**
     * Change the duration of the movie, excluding breaks and advertisements
     * @param newDuration the new duration of the movie, as integer, in minutes
     */
    public void setDuration(int newDuration)
    {
    	this.duration = newDuration;
    }
    
    /**
     * Change the amount of tickets which have been sold for this movie
     * @param newSold the total amount of tickets which have been sold for this movie
     */
    public void setSale(int newSold)
    {
    	this.ticketsSold = newSold;
    }
    
    /**
     * Sets the rating of a movie.
     * No longer used, rating value is calculated based on the ratings given by the users.
     * Every time getRating() is called the rating will automatically be updated to the actual average
     * @param newRating new rating of the movie, this should be a double between 1.0 and 5.0
     */
    @Deprecated
    public void setRating(double newRating)
    {
    	this.averageRating = newRating;
    }
    
    /**
     * Determines if the movie can be booked by users.
     * Movies which have a status of "Coming Soon" or "End Of Showing" can not be booked by the moviegoers.
     * @return boolean: can the movie be booked by a moviegoer.
     */
    public boolean canBook() 
    {
        String[] possibleStatus = MovieControl.getValidMovieStatus();
        return ((this.status.equals(possibleStatus[0]))||(this.status.equals(possibleStatus[possibleStatus.length-2])));
    }
    
    /**
     * Get the ratings given by the moviegoers of this movie 
     * @return ArrayList of Rating objects
     */
    public ArrayList<Rating> getRatings() {return ratings;}
    
    /**
     * Get the showings of this movie.
     * Showings can be spread over multiple cinemas and dates
     * @return ArrayList of MovieShowing objects
     */
    public ArrayList<MovieShowing> getShowings() {return showings;}
   
    /**
     * Update the ArrayList of MovieShowing objects.
     * use of this method is possible but discouraged, instead directly manipulate the ArrayList of MovieShowing of the class 
     * @param newShowings The updated ArrayList of MovieShowing objects for this movie
     */
    @Deprecated
    public void setShowings(ArrayList<MovieShowing> newShowings) {showings = newShowings;}
    
 
    /**
     * Compares the ticketsale of this movie to a different one.
     * Used for sorting purposes.
     * @param o Movie object to compare this movie to
     * @return boolean logic used for sorting
     */
    public Boolean compareToByTicketsSold(Movie o){
        if(o.getTicketsSold()<=getTicketsSold())
            return false;
        return true;
    }

    /**
     * Compares the rating of this movie to a different one.
     * Used for sorting purposes.
     * @param o Movie object to compare this movie to.
     * @return boolean logic used for sorting
     */
    public Boolean compareToByRatings(Movie o){
        if(o.getRating()<=getRating())
            return false;
        return true;
    }

}
