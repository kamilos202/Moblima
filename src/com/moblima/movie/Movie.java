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
    private double duration;
    private ArrayList<MovieShowing> showings;
    private ArrayList<Rating> ratings = new ArrayList<Rating>();
    private double averageRating;


    public Movie(String title, String synopString, String director, String cast, String status, double duration,ArrayList<MovieShowing> showings){
    	
    	System.out.println("initiate movie");
        this.title = title;
        this.synopString = synopString;
        this.director = director;
        this.cast = cast;
        this.status = status;
        this.rating = 0.0;  //to be retrieved from rating.txt
        this.duration = duration;
        this.showings = showings;
        retrieveRatingsFromDatabase();
        showRatings();
    }
    
    public void retrieveRatingsFromDatabase()
    {
    	System.out.println("Title " + title);
    	List<String> moviesWithRating = DataBaseCommunication.readFile("ratings.txt");
    	String[] ratingString = null;
    	System.out.println("Ratings:" + moviesWithRating.get(0));
    	for(int i =0;i<moviesWithRating.size();i++)
    	{
    		System.out.println(moviesWithRating.get(i).split(";")[0]);
    		if(moviesWithRating.get(i).split(";")[0].equals(this.title))
    		{
    			ratingString = moviesWithRating.get(i).substring(moviesWithRating.get(i).split(";")[0].length()+1).split(";");
    			break;
    		}
    	}
    	System.out.println();
    	if(!(ratingString == null))
    	{
    		for(int j=0;j<ratingString.length;j++)
        	{
        		String[] ratingInfo = ratingString[j].split("\\|");
        		System.out.println("ratingString " + ratingString[j]);
        		System.out.println("ratingInfo: " + ratingInfo[0]);
        		ratings.add(new Rating(User.getUserByName(ratingInfo[0]),Double.parseDouble(ratingInfo[1]),ratingInfo[2]));
        	}
        	
        	updateAverageRating();
    	}
    	
    }
    
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
    
    public ArrayList<Rating> getRatings() {return ratings;}
    
    public void showRatings()
    {
    	System.out.println("Ratings allready given: ");
    	for(int i=0;i<ratings.size();i++)
    	{
    		System.out.println(ratings.get(i).toString());
    	}
    }

    public ArrayList<MovieShowing> getShowings() {return showings;}
    
    public void setShowings(ArrayList<MovieShowing> newShowings) {showings = newShowings;}
    
    

}
