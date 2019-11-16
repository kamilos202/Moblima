package com.moblima.rating;
import com.moblima.user.User;

/**
 * Rating is an entity class.
 * It contains the rating of a user and is saved in the movie instance.
 * @author Ivo Janssen
 *
 */
public class Rating 
{
	
	private double score;
	private String description;
	private User user;
	
	/**
	 * Construct a rating based on the given information
	 * @param user the user who gives the rating
	 * @param score the score given by the user on a scale from 1.0 to 5.0
	 * @param description Any further remarks given by the user
	 */
	public Rating(User user, double score, String description)
	{
		this.score = score;
		this.description = description;
		this.user = user;
		System.out.println(" given rating: " + user.getUsername()+score+description);
	}
	
	
	/**
	 * getters and setters for rating
	 */
	public double getScore() {return score;}
	
	public String getDescription() {return description;}
	
	public User getUser() {return user;}
	
	public String toString() {return getUser().getUsername()+"|"+ getScore()+ "|"+getDescription();}
}
