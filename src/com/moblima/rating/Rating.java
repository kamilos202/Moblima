package com.moblima.rating;
import com.moblima.user.User;

public class Rating 
{
	
	private double score;
	private String description;
	private User user;
	
	
	public Rating(User user, double score, String description)
	{
		this.score = score;
		this.description = description;
		this.user = user;
		System.out.println(" given rating: " + user.getUsername()+score+description);
	}
	
	public double getScore() {return score;}
	
	public String getDescription() {return description;}
	
	public User getUser() {return user;}
	
	public String toString() {return getUser().getUsername()+" "+ getScore()+ " "+getDescription();}
}
