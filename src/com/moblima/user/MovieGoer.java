package com.moblima.user;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.moblima.booking.Booking;
import com.moblima.booking.BookingPage;
import com.moblima.movie.Movie;
import com.moblima.movie.MovieListing;
import com.moblima.rating.Rating;
import com.moblima.util.UserInputs;

/**
 * MovieGoer is an entity class which extends from the generalized User entity.
 * Its main purpose is to differentiate between a admin in order to perform the standard MovieGoer functions.
 * Next to that the moviegoer class can in future expension of the program be used to store more information regarding to the moviegoer object, for
 * Example: user seat preferences could be stored or creditcard details can be saved to make payments faster.
 * @author Ivo Janssen
 *
 */
public class MovieGoer extends User {

	Date birthdate;
	MovieGoer current;
	
	UserBoundary boundary;

	/**
	 * 
	 * @param username
	 * @param password
	 * @param id
	 * @param birthdate
	 * @throws ParseException
	 */
	public MovieGoer(String username, String password, int id, String birthdate) throws ParseException {
		super(username,password,id);
		SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
		this.birthdate = formatter.parse(birthdate);
		boundary = new MovieGoerBoundary(this);
		System.out.println("I am a moviegoer");
	}

	@Override
	public UserBoundary getBoundary()
	{
		return boundary;
	}
	/**
	 * 
	 */
	@Override
	public String getUsername() {
		return this.username;
	}
	
	@Override
	public Date getBirthdate()
	{
		return this.birthdate;
	}


	
	/**
	 * 
	 */
	

}
