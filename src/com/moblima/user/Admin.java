package com.moblima.user;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.moblima.database.DataBase;
import com.moblima.movie.Movie;
import com.moblima.movie.MovieListing;
import com.moblima.util.UserInputs;

/**
 * Admin is an entity class which extends from the generalized User entity.
 * Its main purpose is to differentiate between a moviegoer in order to perform authorized admin functions.
 * Next to that the admin class can in future expension of the program be used to store more information regarding to the admin object, for
 * Example: information about which cineplexes an admin can influence can be saved in the admin entity
 * @author Ivo Janssen
 *
 */
public class Admin extends User {

	private UserBoundary boundary;
	
	
	/**
	 * 
	 * @param username the username of the admin
	 * @param password his password
	 * @param id his userID
	 */
	public Admin(String username, String password, int id) {
		super(username,password,id);
		boundary = new AdminBoundary(this);
		System.out.println("I am an admin");
	}
	
	/**
	 * This boundary defines the functions the admin can perform, by initiating this boundary as type AdminBoundary, when LoggedIn this user will
	 * have access to all Admin functions
	 */
	@Override
	public UserBoundary getBoundary()
	{
		return boundary;
	}


}
