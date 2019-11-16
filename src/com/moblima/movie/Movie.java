package com.moblima.movie;
import java.util.ArrayList;
import com.moblima.rating.Rating;
/**
 * Movie class represents movie which then can be showed in Cinema
 */
public class Movie 
{
    private String title;
    private String synopString;
    private String director;
    private String cast;
    private String status;
    private String type;
    private int duration;
    private ArrayList<MovieShowing> showings;
    private ArrayList<Rating> ratings = new ArrayList<Rating>();
    private double averageRating;
    private int ticketsSold;

    /**
     * Movie class constructor 
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
     * Getters and setters for Movie class
    **/

    
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
    public void setType(String validLineInput) {
        type = validLineInput;
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
    public boolean canBook() 
    {
        String[] possibleStatus = MovieControl.getValidMovieStatus();
        return ((this.status.equals(possibleStatus[0]))||(this.status.equals(possibleStatus[possibleStatus.length-2])));
    }
    public ArrayList<Rating> getRatings() {return ratings;}
    public ArrayList<MovieShowing> getShowings() {return showings;}
    /**
     * Set showing
     * @param newShowings
     */
    public void setShowings(ArrayList<MovieShowing> newShowings) {showings = newShowings;}
    /**
     * Method used for Sorting by number of ticket sold;
     * @param o
     * @return true or false
     */
    public Boolean compareToByTicketsSold(Movie o){
        if(o.getTicketsSold()<=getTicketsSold())
            return false;
        return true;
    }
    /**
     * Method used for Sorting by average rating
     * @param o
     * @return true or false
     */
    public Boolean compareToByRatings(Movie o){
        if(o.getRating()<=getRating())
            return false;
        return true;
    }

}
