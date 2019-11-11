package com.moblima.movie;

import java.util.ArrayList;
import java.util.List;

import com.moblima.database.DataBaseCommunication;
import com.moblima.rating.Rating;
import com.moblima.user.User;

public class Movie 
{
    private String title;
    private String synopString;
    private String director;
    private String cast;
    private String status;
    private double rating;
    private String movieType;
    private int duration;
    private ArrayList<MovieShowing> showings;
    private ArrayList<Rating> ratings = new ArrayList<Rating>();
    private double averageRating;

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
    public Movie(String title, String synopString, String status,String director, String cast, int duration,ArrayList<MovieShowing> showings){
    	
    	System.out.println("initiate movie");
        this.title = title;
        this.synopString = synopString;
        this.director = director;
        this.cast = cast;
        this.status = status;
        this.rating = 0.0;  //to be retrieved from rating.txt
        this.duration = duration;
        this.showings = showings;
        System.out.println("------------------------------------");
        retrieveRatingsFromDatabase();
        showRatings();
    }
    /**
     * 
     */
    public void retrieveRatingsFromDatabase()
    {
    	System.out.println("Title " + title);
    	List<String> moviesWithRating = DataBaseCommunication.readFile("ratings.txt");
    	String[] ratingString = null;
    	System.out.println("Ratings:" + moviesWithRating.get(0));
    	for(int i =0;i<moviesWithRating.size();i++)
    	{
    		System.out.println(" We are looking for this"+moviesWithRating.get(i).split(";")[0]);
    		if(moviesWithRating.get(i).split(";")[0].equals(this.title))
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
                System.out.println(" The user who gives a rating: " + User.getUserByName(ratingInfo[0]).getUsername());
        		ratings.add(new Rating(User.getUserByName(ratingInfo[0]),Double.parseDouble(ratingInfo[1]),ratingInfo[2]));
        	}
        	
        	updateAverageRating();
    	}
    	
    }
    /**
     * 
     * @param user
     * @param score
     * @param description
     */
    public void addRating(User user, double score, String description)
    {
    	ratings.add(new Rating(user,score,description));
    	List<String> currentRatings = DataBaseCommunication.readFile("ratings.txt");
    	String[] linesToWrite = new String[currentRatings.size()];
    	for(int i = 0;i<currentRatings.size();i++)
    	{
    		if(currentRatings.get(i).split(";")[0].equals(this.title)) 
    			currentRatings.set(i, currentRatings.get(i)+";"+user.getUsername()+"|"+score+"|"+description);
    		linesToWrite[i] = currentRatings.get(i)+"\n";
    	}
    	DataBaseCommunication.writeToDataBase(linesToWrite, "ratings.txt");
    	updateAverageRating();
    	showRatings();
    }
    /**
     * 
     */
    public void updateAverageRating()
    {
    	averageRating = 0;
    	for(int i = 0;i<ratings.size();i++)
    	{
    		System.out.println("calculate average: " + ratings.get(i).getScore());
    		averageRating += ratings.get(i).getScore();
    		System.out.println(ratings.get(i).getDescription());
    	}
    	System.out.println("sum:" + averageRating);
    	System.out.println("average: "+ averageRating / ((double)ratings.size()));
    	System.out.println(Math.round(1000*(averageRating / ((double)ratings.size()))));
    	averageRating = Math.round(100*(averageRating / ((double)ratings.size())))/100.;
    	System.out.println("the average Rating of " + title+ "is: " + averageRating);
    }

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
        return rating;
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
    
    public ArrayList<Rating> getRatings() {return ratings;}
    public ArrayList<MovieShowing> getShowings() {return showings;}

    /**
     * Displaying movie ratings
     */
    public void showRatings()
    {
    	System.out.println("Ratings allready given: ");
    	for(int i=0;i<ratings.size();i++)
    	{
    		System.out.println(ratings.get(i).toString());
    	}
    }

    /**
     * Set showing
     * @param newShowings
     */
    public void setShowings(ArrayList<MovieShowing> newShowings) {showings = newShowings;}
    
    public String toDataBaseString()
    {
    	//System.out.println("STATUS: " + this.status);
    	String result = "TITLE:"+this.title+";SYNOPSIS:"+this.synopString+";STATUS:"+this.status+";DIRECTOR:"+this.director+";CAST:"+this.cast+";DURATION:"+
    				this.duration+";SHOWINGS:";
    	
    	for(int i =0;i<showings.size();i++)
    	{
    		if(!showings.get(i).isCopy())
    		{
    			result = result + showings.get(i).toString() + "/";
    		}
    	}
    	result = (String) result.subSequence(0, result.length()-1) + ";";
    	//System.out.println("result: " + result);
    	//System.out.println("result: " + DataBaseCommunication.readFile("movies.txt").get(0));
    	return result;
    }
    
    

}
