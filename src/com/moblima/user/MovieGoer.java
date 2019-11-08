package com.moblima.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.moblima.movie.Movie;
import com.moblima.movie.MovieListing;
import com.moblima.rating.Rating;
import com.moblima.util.UserInputs;

public class MovieGoer extends User 
{
	
	String username,password;
	Date birthdate;
	int id;
	
	public MovieGoer(String username, String password, int id, String birthdate) throws ParseException
	{
		this.username = username;
		this.password = password;
		this.id = id;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
		this.birthdate = formatter.parse(birthdate);
		
		System.out.println("I am a moviegoer");
	}
	
	@Override
	public String getUsername() {return username;}
	
	@Override
	public void performActions()
	{
		System.out.println("these are the moviegoer actions");
		boolean loggedIn = true;
		while(loggedIn)
		{
			System.out.println("What action do you want to perform: ");
			System.out.println("1: Rate a movie");
			System.out.println("8: logout");
			
			int actionChoice = UserInputs.getValidIntegerInput();
			switch(actionChoice)
			{
				case 1:
					giveRating();
					break;
				case 8:
					loggedIn = false;
					break;
				default:
					System.out.println("Please enter one of the valid options");
					break;
			}
		}
	}
	
	public void giveRating()
	{
		MovieListing.addRatingToMovie(this);
	}

}
